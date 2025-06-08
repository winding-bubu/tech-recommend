package com.tech.recommend.domain.biz.scene.executor;

import com.tech.recommend.common.configuration.config.ChannelConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationLoader;
import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.common.thread.pool.DynamicThreadPool;
import com.tech.recommend.domain.api.biz.IChannelRecallService;
import com.tech.recommend.domain.api.context.ChannelContext;
import com.tech.recommend.domain.api.context.SceneContext;
import com.tech.recommend.facade.model.ResultItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * 场景召回执行
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Slf4j
@Component
public class SceneRecallExecutor {

    @Resource
    private ConfigurationLoader configurationLoader;

    @Resource
    private IChannelRecallService channelRecallService;

    /**
     * 渠道召回兜底超时时间
     */
    private static final int CHANNEL_DEFAULT_TIMEOUT = 200;

    /**
     * 场景召回执行
     * 
     * @param sceneContext 上下文
     */
    public void recall(SceneContext sceneContext) {

        // 召回配置获取
        List<ChannelConfig> channelConfigs = configurationLoader.getChannelConfigs(sceneContext.getSceneId());
        if (CollectionUtils.isEmpty(channelConfigs)) {
            throw new TechRecommendException(ErrorCodeEnum.CHANNEL_LACK);
        }
        Map<String, ChannelConfig> channelConfigMap =
            channelConfigs.stream().collect(Collectors.toMap(ChannelConfig::getChannelId, channelConfig -> channelConfig));

        // 多线程召回执行
        Map<String, CompletableFuture<List<ResultItem>>> futures = new HashMap<>();
        DynamicThreadPool sceneThreadPool = configurationLoader.getSceneThreadPool(sceneContext.getSceneId());
        for (ChannelConfig channelConfig : channelConfigs) {
            // 构建上下文
            ChannelContext context = new ChannelContext();
            context.setRequestId(sceneContext.getRequestId());
            context.setSceneId(sceneContext.getSceneId());
            context.setChannelId(channelConfig.getChannelId());
            context.setTimeout(channelConfig.getTimeout());
            context.setRecallNum(channelConfig.getRecallNum());
            context.setParams(sceneContext.cloneParams());
            // 异步执行渠道召回
            CompletableFuture<List<ResultItem>> future = CompletableFuture.supplyAsync(() -> {
                channelRecallService.recall(context);
                return context.getResultItems();
            }, sceneThreadPool.getExecutor());
            futures.put(channelConfig.getChannelId(), future);
        }

        // 获取召回结果
        Map<String, List<ResultItem>> resultItemsMap = new LinkedHashMap<>();
        for (Map.Entry<String, CompletableFuture<List<ResultItem>>> entry : futures.entrySet()) {
            String channelId = entry.getKey();
            CompletableFuture<List<ResultItem>> future = entry.getValue();
            ChannelConfig channelConfig = channelConfigMap.get(channelId);
            // 根据设定超时时间进行处理
            int timeout = Objects.isNull(channelConfig.getTimeout()) ? CHANNEL_DEFAULT_TIMEOUT : channelConfig.getTimeout();
            try {
                List<ResultItem> resultItems = future.get(timeout, TimeUnit.MILLISECONDS);
                resultItemsMap.put(channelId, resultItems);
            } catch (InterruptedException e) {
                log.error("channel:{} recall interrupted", channelId, e);
                Thread.currentThread().interrupt();
                future.cancel(true);
            } catch (TimeoutException e) {
                log.error("channel:{} recall timeout in {} ms", channelId, timeout, e);
                future.cancel(true);
            } catch (ExecutionException e) {
                log.error("channel:{} recall occur error", channelId, e);
            }
        }

        // 合并
        Map<String, ResultItem> resultItemMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<ResultItem>> entry : resultItemsMap.entrySet()) {
            List<ResultItem> channelResults = entry.getValue();
            if (CollectionUtils.isEmpty(channelResults)) {
                continue;
            }
            for (ResultItem resultItem : channelResults) {
                if (resultItemMap.containsKey(resultItem.getItemId())) {
                    // 货源合并处理
                    this.resultItemsMerge(resultItemMap.get(resultItem.getItemId()), resultItem);
                } else {
                    resultItemMap.put(resultItem.getItemId(), resultItem);
                }
            }
        }

        sceneContext.setResultItems(new ArrayList<>(resultItemMap.values()));
    }

    private void resultItemsMerge(ResultItem existItem, ResultItem addedItem) {

        // 召回路标识合并
        existItem.getRecallTags().addAll(addedItem.getRecallTags());

    }

}

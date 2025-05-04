package com.tech.recommend.domain.biz.scene.service;

import com.tech.recommend.common.configuration.config.ChannelConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationFactory;
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
import java.util.concurrent.TimeUnit;
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
    private ConfigurationFactory configurationFactory;
    
    @Resource
    private IChannelRecallService channelRecallService;

    /**
     * 场景召回执行
     * 
     * @param sceneContext 上下文
     */
    public void recall(SceneContext sceneContext) {

        // 召回配置获取
        List<ChannelConfig> channelConfigs = configurationFactory.getChannelConfigs(sceneContext.getSceneId());
        if (CollectionUtils.isEmpty(channelConfigs)) {
            throw new TechRecommendException(ErrorCodeEnum.CHANNEL_LACK);
        }
        Map<String, ChannelConfig> channelConfigMap = channelConfigs.stream()
                .collect(Collectors.toMap(ChannelConfig::getChannelId, channelConfig -> channelConfig, (a, b) -> b));

        // 多线程召回执行
        Map<String, CompletableFuture<List<ResultItem>>> futures = new HashMap<>();
        DynamicThreadPool sceneThreadPool = configurationFactory.getSceneThreadPool(sceneContext.getSceneId());
        for (ChannelConfig channelConfig : channelConfigs) {
            // 构建上下文
            ChannelContext context = new ChannelContext();
            context.setRequestId(sceneContext.getRequestId());
            context.setSceneId(sceneContext.getSceneId());
            context.setChannelId(channelConfig.getChannelId());
            context.setParams(sceneContext.cloneParams());
            // 异步执行渠道召回
            CompletableFuture<List<ResultItem>> future = CompletableFuture.supplyAsync(() -> {
                channelRecallService.recall(context);
                return context.getResultItems();
            }, sceneThreadPool.getExecutor());
            futures.put(channelConfig.getChannelId(), future);
        }
        
        // 获取召回结果
        Map<String, List<ResultItem>> resultItemsMap = new HashMap<>();
        for (Map.Entry<String, CompletableFuture<List<ResultItem>>> entry : futures.entrySet()) {
            String channelId = entry.getKey();
            CompletableFuture<List<ResultItem>> future = entry.getValue();
            ChannelConfig channelConfig = channelConfigMap.get(channelId);
            // 根据设定超时时间进行处理
            int timeout = Objects.isNull(channelConfig.getTimeout()) ? 200 : channelConfig.getTimeout();
            try {
                List<ResultItem> resultItems = future.get(timeout, TimeUnit.MILLISECONDS);
                resultItemsMap.put(channelId, resultItems);
            } catch (InterruptedException e) {
                log.error("scene recall interrupted", e);
            } catch (Exception e) {
                log.error("scene recall occur error", e);
            }
        }

        // 合并
        Map<String, ResultItem> resultItemMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<ResultItem>> entry : resultItemsMap.entrySet()) {
            List<ResultItem> channelResults = entry.getValue();
            for (ResultItem resultItem : channelResults) {
                if (resultItemMap.containsKey(resultItem.getItemId())) {
                    // 仅合并召回路标识
                    resultItemMap.get(resultItem.getItemId()).getRecallTags().addAll(resultItem.getRecallTags());
                } else {
                    resultItemMap.put(resultItem.getItemId(), resultItem);
                }
            }
        }

        sceneContext.setResultItems(new ArrayList<>(resultItemMap.values()));
    }

}

package com.tech.recommend.domain.biz.channel.executor;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationLoader;
import com.tech.recommend.domain.api.biz.IGenericService;
import com.tech.recommend.domain.api.context.GenericContext;
import com.tech.recommend.domain.api.model.generic.GenericResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import com.tech.recommend.domain.api.context.ChannelContext;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道泛化执行
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Component
public class ChannelGenericExecutor {
    
    @Resource
    private ConfigurationLoader configurationLoader;

    @Resource
    private IGenericService genericService;

    /**
     * 渠道泛化执行
     * 
     * @param context 上下文
     */
    public void generic(ChannelContext context) {

        // 泛化配置获取
        List<GenericConfig> channelGenericConfigs =
            configurationLoader.getChannelGenericConfigs(context.getSceneId(), context.getChannelId());
        if (CollectionUtils.isEmpty(channelGenericConfigs)) {
            return;
        }

        // 上下文转换
        List<String> genericIds = channelGenericConfigs.stream()
                .map(GenericConfig::getGenericId)
                .collect(Collectors.toList());
        GenericContext genericContext = new GenericContext();
        genericContext.setRequestId(context.getRequestId());
        genericContext.setSourceType("channel");
        genericContext.setSceneId(context.getSceneId());
        genericContext.setChannelId(context.getChannelId());
        genericContext.setGenericIds(genericIds);
        genericContext.setParams(context.cloneParams());

        // 执行泛化
        GenericResponse genericResponse = genericService.generic(genericContext);
        if (Objects.isNull(genericResponse) || MapUtils.isEmpty(genericResponse.getData())) {
            return;
        }

        // 结果处理
        context.getParams().putAll(genericResponse.getData());
    }

}

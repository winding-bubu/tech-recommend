package com.tech.recommend.domain.biz.channel.executor;

import com.tech.recommend.common.configuration.config.EnhanceConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationLoader;
import com.tech.recommend.domain.api.biz.IEnhanceService;
import com.tech.recommend.domain.api.context.ChannelContext;
import com.tech.recommend.domain.api.context.EnhanceContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 渠道增强处理
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Component
public class ChannelEnhanceExecutor {

    @Resource
    private ConfigurationLoader configurationLoader;

    @Resource
    private IEnhanceService enhanceService;
    
    /**
     * 渠道增强执行
     * 
     * @param context 渠道上下文
     */
    public void enhance(ChannelContext context) {
        
        // 增强配置获取
        List<EnhanceConfig> channelEnhanceConfigs =
            configurationLoader.getChannelEnhanceConfigs(context.getSceneId(), context.getChannelId());
        if (CollectionUtils.isEmpty(channelEnhanceConfigs)) {
            return;
        }

        // 上下文获取
        List<String> enhanceIds = channelEnhanceConfigs.stream()
                .map(EnhanceConfig::getEnhanceId)
                .collect(Collectors.toList());
        EnhanceContext enhanceContext = new EnhanceContext();
        enhanceContext.setRequestId(context.getRequestId());
        enhanceContext.setSourceType("channel");
        enhanceContext.setSceneId(context.getSceneId());
        enhanceContext.setChannelId(context.getChannelId());
        enhanceContext.setEnhanceIds(enhanceIds);
        enhanceContext.setResultItems(context.getResultItems());

        // 执行
        enhanceService.enhance(enhanceContext);
    }

}

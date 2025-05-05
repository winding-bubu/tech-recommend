package com.tech.recommend.domain.biz.channel.executor;

import com.tech.recommend.common.configuration.config.TemplateConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationFactory;
import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.domain.api.biz.ITemplateRecallService;
import com.tech.recommend.domain.api.context.TemplateContext;
import org.springframework.stereotype.Component;

import com.tech.recommend.domain.api.context.ChannelContext;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 渠道召回执行
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Component
public class ChannelRecallExecutor {
    
    @Resource
    private ConfigurationFactory configurationFactory;
    
    @Resource
    private ITemplateRecallService templateRecallService;

    /**
     * 召回执行
     * 
     * @param context 渠道上下文
     */
    public void recall(ChannelContext context) {

        // 召回配置获取
        TemplateConfig templateConfig = configurationFactory.getChannelTemplateConfig(context.getSceneId(), context.getChannelId());
        if (Objects.isNull(templateConfig)) {
            throw new TechRecommendException(ErrorCodeEnum.TEMPLATE_LACK);
        }

        // 构建上下文
        TemplateContext templateContext = new TemplateContext();
        templateContext.setRequestId(context.getRequestId());
        templateContext.setSceneId(context.getSceneId());
        templateContext.setChannelId(context.getChannelId());
        templateContext.setTemplateId(templateConfig.getTemplateId());
        templateContext.setTimeout(context.getTimeout());
        templateContext.setRecallNum(context.getRecallNum());
        templateContext.setParams(context.cloneParams());

        // 召回执行
        templateRecallService.recall(templateContext);
    }

}

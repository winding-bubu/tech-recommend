package com.tech.recommend.service.enhance.impl;

import com.tech.recommend.common.configuration.config.EnhanceConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationLoader;
import com.tech.recommend.domain.api.context.EnhanceContext;
import com.tech.recommend.domain.api.service.IEnhanceProcessor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 增强节点抽象层
 *
 * @author winding bubu
 * @since 2025/06/22
 */
@Slf4j
public abstract class AbstractEnhanceProcessor implements IEnhanceProcessor {

    @Resource
    private ConfigurationLoader configurationLoader;

    @Override
    public void enhance(EnhanceContext enhanceContext) {
        try {
            // 获取增强配置
            EnhanceConfig enhanceConfig = configurationLoader.getEnhanceConfig(enhanceContext.getSceneId(), enhanceId());
            // 执行增强
            this.execute(enhanceConfig, enhanceContext);
        } catch (Exception e) {
            log.error("{} doEnhance occurred exception", enhanceId(), e);
        }
    }

    /**
     * 执行增强
     * 
     * @param enhanceConfig 增强配置
     * @param enhanceContext 增强上下文
     */
    protected abstract void execute(EnhanceConfig enhanceConfig, EnhanceContext enhanceContext);

}

package com.tech.recommend.service.generic.impl;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationFactory;
import com.tech.recommend.domain.api.context.GenericContext;
import com.tech.recommend.domain.api.service.IGenericProcessor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 泛化处理抽象层
 *
 * @author winding bubu
 * @since 2025/05/06
 */
@Slf4j
public abstract class AbstractGenericProcessor implements IGenericProcessor {

    @Resource
    private ConfigurationFactory configurationFactory;
    
    @Override
    public Map<String, Object> doGeneric(GenericContext genericContext) {
        try {
            // 获取当前泛化配置
            GenericConfig genericConfig = configurationFactory.getGenericConfig(genericContext.getSceneId(), genericId());
            // 执行泛化
            return this.execute(genericConfig, genericContext);
        } catch (Exception e) {
            log.error("{} doGeneric occurred exception", genericId(), e);
            return new HashMap<>();
        }
    }

    /**
     * 泛化执行
     * 
     * @param genericConfig 泛化基础配置
     * @param genericContext 泛化上下文
     * @return 泛化结果
     */
    protected abstract Map<String, Object> execute(GenericConfig genericConfig, GenericContext genericContext);

}

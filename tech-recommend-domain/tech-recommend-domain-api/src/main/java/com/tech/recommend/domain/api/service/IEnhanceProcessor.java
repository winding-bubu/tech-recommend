package com.tech.recommend.domain.api.service;

import com.tech.recommend.domain.api.context.EnhanceContext;

/**
 * 具体增强执行
 *
 * @author winding bubu
 * @since 2025/05/04
 */
public interface IEnhanceProcessor {

    /**
     * 增强执行
     * 
     * @param enhanceContext 上下文
     */
    void enhance(EnhanceContext enhanceContext);

    /**
     * 增强ID
     * 
     * @return 增强ID
     */
    String enhanceId();

}

package com.tech.recommend.domain.api.biz;

import com.tech.recommend.domain.api.context.EnhanceContext;

/**
 * 增强服务
 *
 * @author winding bubu
 * @since 2025/05/04
 */
public interface IEnhanceService {

    /**
     * 增强节点处理
     * 
     * @param enhanceContext 上下文
     */
    void enhance(EnhanceContext enhanceContext);
    
}

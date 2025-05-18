package com.tech.recommend.domain.api.service;

import com.tech.recommend.common.constant.DslBuildEnum;
import com.tech.recommend.domain.api.model.dsl.DslBuildRequest;
import com.tech.recommend.domain.api.model.dsl.DslBuildResponse;

/**
 * dsl语句构建处理
 *
 * @author winding bubu
 * @since 2025/05/18
 */
public interface IDslBuildProcessor {

    /**
     * 构建dsl
     * 
     * @param dslBuildRequest dsl请求
     * @return dsl结果
     */
    DslBuildResponse build(DslBuildRequest dslBuildRequest);

    /**
     * 构建类型
     * 
     * @see DslBuildEnum
     * @return 类型
     */
    String type();

}

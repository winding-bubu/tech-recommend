package com.tech.recommend.domain.api.service;

import com.tech.recommend.domain.api.context.GenericContext;

import java.util.Map;

/**
 * 具体泛化执行
 *
 * @author winding bubu
 * @since 2025/05/04
 */
public interface IGenericProcessor {

    /**
     * 泛化执行
     * 
     * @param genericContext 泛化上下文
     * @return 泛化结果
     */
    Map<String, Object> doGeneric(GenericContext genericContext);

    /**
     * 泛化ID
     * 
     * @return 泛化ID
     */
    String genericId();

}

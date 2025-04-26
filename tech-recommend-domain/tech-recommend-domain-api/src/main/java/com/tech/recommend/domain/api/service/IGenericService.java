package com.tech.recommend.domain.api.service;

import com.tech.recommend.domain.api.context.GenericContext;
import com.tech.recommend.domain.api.model.generic.GenericResponse;

/**
 * 泛化服务
 *
 * @author winding bubu
 * @since 2025/04/26
 */
public interface IGenericService {

    /**
     * 泛化策略执行
     *
     * @param genericContext 泛化上下文
     */
    GenericResponse generic(GenericContext genericContext);

}

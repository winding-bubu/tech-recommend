package com.tech.recommend.domain.api.model.generic;

import lombok.Data;

import java.util.Map;

/**
 * 泛化执行结果
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@Data
public class GenericResponse {

    /**
     * 执行结果集合
     */
    private Map<String, Object> data;

}

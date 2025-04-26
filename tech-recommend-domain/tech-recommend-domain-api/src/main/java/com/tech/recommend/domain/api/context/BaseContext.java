package com.tech.recommend.domain.api.context;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础上下文
 *
 * @author winding bubu
 * @since 2025/04/24
 */
@Data
public class BaseContext {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 召回基础参数
     */
    private Map<String, Object> params = new HashMap<>();

}

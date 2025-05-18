package com.tech.recommend.domain.api.model.dsl;

import com.tech.recommend.common.configuration.config.DslConfig;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * dsl构建请求
 *
 * @author winding bubu
 * @since 2025/05/18
 */
@Data
public class DslBuildRequest {

    /**
     * dsl配置
     */
    private DslConfig dslConfig;

    /**
     * 召回基础参数
     */
    private Map<String, Object> params = new HashMap<>();

}

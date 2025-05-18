package com.tech.recommend.common.configuration.config;

import lombok.Data;

/**
 * dsl模板配置
 *
 * @author winding bubu
 * @since 2025/05/05
 */
@Data
public class DslConfig {

    /**
     * ID
     */
    private String id;

    /**
     * dsl语句
     */
    private String dsl;

    /**
     * dsl构建类型
     */
    private String type;

}

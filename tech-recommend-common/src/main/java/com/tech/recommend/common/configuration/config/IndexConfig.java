package com.tech.recommend.common.configuration.config;

import lombok.Data;

/**
 * 索引配置
 *
 * @author winding bubu
 * @since 2025/05/05
 */
@Data
public class IndexConfig {

    /**
     * 索引ID
     */
    private String indexId;

    /**
     * 类型
     * 
     * @see com.tech.recommend.common.constant.IndexTypeEnum
     */
    private String type;

    /**
     * 描述
     */
    private String description;

}

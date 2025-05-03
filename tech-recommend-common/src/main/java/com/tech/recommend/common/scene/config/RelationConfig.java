package com.tech.recommend.common.scene.config;

import lombok.Data;

/**
 * 关联关系配置
 *
 * @author winding bubu
 * @since 2025/05/03
 */
@Data
public class RelationConfig {

    /**
     * 关联类型
     */
    private String relationType;

    /**
     * 关联来源
     */
    private String relationFrom;

    /**
     * 关联目标
     */
    private String relationTo;

}

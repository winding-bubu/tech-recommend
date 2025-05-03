package com.tech.recommend.common.scene.config;

import lombok.Data;

/**
 * 场景配置
 *
 * @author winding bubu
 * @since 2025/05/03
 */
@Data
public class SceneConfig {

    /**
     * 场景ID
     */
    private String sceneId;

    /**
     * 召回数量
     */
    private Integer recallNum;

    /**
     * 场景描述
     */
    private String description;

}

package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * 关联关系枚举
 *
 * @author winding bubu
 * @since 2025/05/03
 */
@Getter
public enum RelationEnum {

    /**
     * 场景 - 渠道
     */
    SCENE_CHANNEL("scene_channel"),

    /**
     * 场景 - 泛化
     */
    SCENE_GENERIC("scene_generic"),

    /**
     * 场景 - 增强
     */
    SCENE_ENHANCE("scene_enhance"),

    /**
     * 场景 - 线程池
     */
    SCENE_POOL("scene_pool"),

    /**
     * 渠道 - 泛化
     */
    CHANNEL_GENERIC("channel_generic"),

    /**
     * 渠道 - 增强
     */
    CHANNEL_ENHANCE("channel_enhance"),

    /**
     * 渠道 - 模板
     */
    CHANNEL_TEMPLATE("channel_template"),

    /**
     * 模板 - 索引
     */
    TEMPLATE_INDEX("template_index"),

    /**
     * 模板 - dsl
     */
    TEMPLATE_DSL("template_dsl"),

    ;

    private final String code;

    RelationEnum(String code) {
        this.code = code;
    }

}

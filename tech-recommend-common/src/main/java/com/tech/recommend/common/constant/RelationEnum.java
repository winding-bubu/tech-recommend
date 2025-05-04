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
     * 场景 关联 渠道
     */
    SCENE_CHANNEL("SCENE_CHANNEL"),

    /**
     * 场景 关联 泛化
     */
    SCENE_GENERIC("SCENE_GENERIC"),

    /**
     * 渠道 关联 泛化
     */
    CHANNEL_GENERIC("CHANNEL_GENERIC"),

    /**
     * 场景 关联 增强
     */
    SCENE_ENHANCE("SCENE_ENHANCE"),

    /**
     * 渠道 关联 增强
     */
    CHANNEL_ENHANCE("CHANNEL_ENHANCE"),

    /**
     * 渠道 关联 模板
     */
    CHANNEL_TEMPLATE("CHANNEL_TEMPLATE"),

    ;

    private final String code;

    RelationEnum(String code) {
        this.code = code;
    }

}

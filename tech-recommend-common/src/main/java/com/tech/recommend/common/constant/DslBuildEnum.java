package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * dsl构建方式枚举
 *
 * @author winding bubu
 * @since 2025/05/18
 */
@Getter
public enum DslBuildEnum {

    FROWARD("forward", "正向构建dsl，通过dsl模板拼接参数生成"),

    BACKWARD("backward", "反向组合"),

    ;

    private final String code;

    private final String desc;

    DslBuildEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}

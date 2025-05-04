package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * 增强枚举
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Getter
public enum EnhanceEnum {

    EXAMPLE_ENHANCE1("exampleEnhance1", "增强示例1"),

    EXAMPLE_ENHANCE2("exampleEnhance2", "增强示例2")

    ;

    private final String code;

    private final String desc;

    EnhanceEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}

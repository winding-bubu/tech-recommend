package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * 泛化枚举
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Getter
public enum GenericEnum {

    EXAMPLE_GENERIC1("exampleGeneric1", "示例泛化1"),

    EXAMPLE_GENERIC2("exampleGeneric2", "示例泛化2"),

    ;

    private final String code;

    private final String desc;

    GenericEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}

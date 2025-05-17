package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * 资源ID枚举
 *
 * @author winding bubu
 * @since 2025/05/17
 */
@Getter
public enum ResourceIdEnum {

    ES("es", "es资源查询"),

    ;

    private final String code;

    private final String desc;

    ResourceIdEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}

package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * 资源类型枚举
 *
 * @author winding bubu
 * @since 2025/05/15
 */
@Getter
public enum ResourceTypeEnum {

    RPC("rpc", "rpc 资源"),

    ES("es", "es 资源"),

    ;

    private final String code;

    private final String desc;

    ResourceTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}

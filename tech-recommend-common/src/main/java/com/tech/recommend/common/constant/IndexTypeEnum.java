package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * 索引类型枚举
 *
 * @author winding bubu
 * @since 2025/05/05
 */
@Getter
public enum IndexTypeEnum {

    ES("es", "es查询"),

    RPC("rpc", "rpc查询"),

    ;

    private final String code;

    private final String desc;

    IndexTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}

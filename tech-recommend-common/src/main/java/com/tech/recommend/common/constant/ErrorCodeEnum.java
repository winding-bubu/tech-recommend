package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * 自定义错误枚举
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Getter
public enum ErrorCodeEnum {

    CONFIG_NOT_EXIST(1001, "config not exist"),

    CHANNEL_LACK(1002, "lack of channel config"),

    TEMPLATE_LACK(1003, "lack of template config"),

    ES_ERROR(1004, "es server error"),

    ;

    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}

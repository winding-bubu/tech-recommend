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

    CONFIG_NOT_EXIST(1001, "配置不存在"),

    CHANNEL_LACK(1002, "渠道配置不存在，请检查！"),

    TEMPLATE_LACK(1003, "模板配置不存在，请检查！"),

    ES_ERROR(1004, "es服务异常"),

    SYSTEM_ERROR(1010, "系统异常"),

    ;

    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}

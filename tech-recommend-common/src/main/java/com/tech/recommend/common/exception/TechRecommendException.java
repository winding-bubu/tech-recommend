package com.tech.recommend.common.exception;

import com.tech.recommend.common.constant.ErrorCodeEnum;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Getter
public class TechRecommendException extends RuntimeException {

    private final int code;

    private final String message;

    public TechRecommendException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}

package com.tech.recommend.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 类型转换工具类
 *
 * @author winding bubu
 * @since 2025/05/17
 */
@Slf4j
public class TypeUtil {

    public static String getString(Object value) {
        try {
            if (Objects.isNull(value)) {
                return null;
            }
            return String.valueOf(value);
        } catch (Exception e) {
            log.error("TypeUtil.getString occur error", e);
            return null;
        }
    }

}

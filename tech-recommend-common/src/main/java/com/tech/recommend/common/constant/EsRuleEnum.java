package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * es规则枚举
 *
 * @author winding bubu
 * @since 2025/05/20
 */
@Getter
public enum EsRuleEnum {

    BOOL("bool"),

    MUST("must"),

    MUST_NOT("must_not"),

    SHOULD("should"),

    ;

    private final String rule;

    EsRuleEnum(String rule) {
        this.rule = rule;
    }

    public boolean isMatch(String rule) {
        return this.rule.equals(rule);
    }

}

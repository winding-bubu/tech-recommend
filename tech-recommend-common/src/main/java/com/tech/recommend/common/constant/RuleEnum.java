package com.tech.recommend.common.constant;

import lombok.Getter;

/**
 * es规则枚举
 *
 * @author winding bubu
 * @since 2025/05/20
 */
@Getter
public enum RuleEnum {

    /****** 分子规则 ******/

    BOOL("bool"),

    MUST("must"),

    MUST_NOT("must_not"),

    SHOULD("should"),

    /****** 原子规则 ******/

    TERMS("terms"),

    TERM("term"),

    EXISTS("exists"),

    RANGE("range"),

    MATCH("match"),

    ;

    private final String rule;

    RuleEnum(String rule) {
        this.rule = rule;
    }

    public boolean isMatch(String rule) {
        return this.rule.equals(rule);
    }

}

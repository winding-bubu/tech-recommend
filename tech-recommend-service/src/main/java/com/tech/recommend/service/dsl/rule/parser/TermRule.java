package com.tech.recommend.service.dsl.rule.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.constant.RuleEnum;

/**
 * 规则【term】
 *
 * @author winding bubu
 * @since 2025/05/28
 */
@Component
public class TermRule extends AbstractRule {

    @Override
    protected Object doParse(Object clause, Map<String, Object> params) {
        if (super.notJsonObject(clause)) {
            return null;
        }
        return super.parseObjectClause((JSONObject)clause, params);
    }

    @Override
    public String getRuleName() {
        return RuleEnum.TERM.getRule();
    }

}

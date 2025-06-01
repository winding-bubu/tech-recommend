package com.tech.recommend.service.dsl.rule.parser;

import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.constant.RuleEnum;
import com.tech.recommend.service.dsl.rule.loader.GroupRuleLoader;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 规则【bool】
 *
 * @author winding bubu
 * @since 2025/05/25
 */
@Component
public class BoolRule extends AbstractRule {

    public BoolRule(GroupRuleLoader groupRuleLoader) {
        this.ruleLoader = groupRuleLoader;
    }

    @Override
    protected Object doParse(Object clause, Map<String, Object> params) {
        if (super.notJsonObject(clause)) {
            return null;
        }
        JSONObject parsedClause = new JSONObject();
        // 取出子句，递归解析
        JSONObject jsonClause = (JSONObject)clause;
        jsonClause.forEach((ruleName, ruleClause) -> {
            Object parsedRuleClause = ruleLoader.load(ruleName, ruleClause, params);
            if (Objects.nonNull(parsedRuleClause)) {
                parsedClause.put(ruleName, parsedRuleClause);
            }
        });
        return parsedClause;
    }

    @Override
    public String getRuleName() {
        return RuleEnum.BOOL.getRule();
    }

}

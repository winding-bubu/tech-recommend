package com.tech.recommend.service.dsl.rule.parser;

import com.alibaba.fastjson2.JSONArray;
import com.tech.recommend.common.constant.RuleEnum;
import com.tech.recommend.service.dsl.rule.loader.AtomicRuleLoader;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 规则【must】
 *
 * @author winding bubu
 * @since 2025/05/25
 */
@Component
public class MustRule extends AbstractRule {

    public MustRule(AtomicRuleLoader atomicRuleLoader) {
        this.ruleLoader = atomicRuleLoader;
    }

    @Override
    protected Object doParse(Object clause, Map<String, Object> params) {
        if (super.notJsonArray(clause)) {
            return null;
        }
        return super.parseArrayClause((JSONArray)clause, params);
    }

    @Override
    public String getRuleName() {
        return RuleEnum.MUST.getRule();
    }

}

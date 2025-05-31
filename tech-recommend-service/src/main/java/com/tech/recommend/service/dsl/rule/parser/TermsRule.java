package com.tech.recommend.service.dsl.rule.parser;

import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.constant.EsRuleEnum;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 规则【terms】
 *
 * @author winding bubu
 * @since 2025/05/28
 */
@Component
public class TermsRule extends AbstractRule {

    @Override
    protected Object doParse(Object clause, Map<String, Object> params) {
        if (super.notJsonObject(clause)) {
            return null;
        }
        return super.parseObjectClause((JSONObject)clause, params);
    }

    @Override
    public String getRuleName() {
        return EsRuleEnum.TERMS.getRule();
    }

}

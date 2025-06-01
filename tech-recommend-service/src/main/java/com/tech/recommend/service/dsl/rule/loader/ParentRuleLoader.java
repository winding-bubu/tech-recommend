package com.tech.recommend.service.dsl.rule.loader;

import com.tech.recommend.service.dsl.rule.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 父级规则加载器
 *
 * @author winding bubu
 * @since 2025/06/01
 */
@Component
public class ParentRuleLoader extends AbstractRuleLoader {

    @Autowired
    public void init(BoolRule boolRule,
                     TermsRule termsRule,
                     TermRule termRule) {
        ruleMap.put(boolRule.getRuleName(), boolRule);
        ruleMap.put(termsRule.getRuleName(), termsRule);
        ruleMap.put(termRule.getRuleName(), termRule);
    }

}

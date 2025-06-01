package com.tech.recommend.service.dsl.rule.loader;

import com.tech.recommend.service.dsl.rule.parser.TermRule;
import com.tech.recommend.service.dsl.rule.parser.TermsRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 原子规则加载器
 *
 * @author winding bubu
 * @since 2025/06/01
 */
@Component
public class AtomicRuleLoader extends AbstractRuleLoader {

    @Autowired
    public void init(TermsRule termsRule,
                     TermRule termRule) {
        ruleMap.put(termsRule.getRuleName(), termsRule);
        ruleMap.put(termRule.getRuleName(), termRule);
    }

}

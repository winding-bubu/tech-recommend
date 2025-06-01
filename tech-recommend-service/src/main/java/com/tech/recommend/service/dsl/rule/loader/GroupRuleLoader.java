package com.tech.recommend.service.dsl.rule.loader;

import com.tech.recommend.service.dsl.rule.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 规则组加载器
 *
 * @author winding bubu
 * @since 2025/06/01
 */
@Component
public class GroupRuleLoader extends AbstractRuleLoader {

    @Autowired
    public void init(MustNotRule mustNotRule,
                     MustRule mustRule) {
        ruleMap.put(mustNotRule.getRuleName(), mustNotRule);
        ruleMap.put(mustRule.getRuleName(), mustRule);
    }

}

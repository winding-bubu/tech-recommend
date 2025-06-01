package com.tech.recommend.service.dsl.rule.loader;

import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.service.dsl.rule.IRule;
import com.tech.recommend.service.dsl.rule.IRuleLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 规则加载抽象层
 *
 * @author winding bubu
 * @since 2025/06/01
 */
public abstract class AbstractRuleLoader implements IRuleLoader {
    
    protected Map<String, IRule> ruleMap = new HashMap<>();

    @Override
    public Object load(String ruleName, Object clause, Map<String, Object> params) {
        // 校验规则是否存在
        if (StringUtils.isBlank(ruleName) || Objects.isNull(clause) || !ruleMap.containsKey(ruleName)) {
            throw new TechRecommendException(ErrorCodeEnum.DSL_BUILD_ERROR);
        }
        return ruleMap.get(ruleName).parse(clause, params);
    }
    
}

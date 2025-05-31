package com.tech.recommend.service.dsl.rule;

import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 规则加载类
 *
 * @author winding bubu
 * @since 2025/05/25
 */
@Component
public class RuleLoader {

    private final Map<String, IRule> ruleMap = new HashMap<>();

    public RuleLoader(List<IRule> rules) {
        rules.forEach(rule -> ruleMap.put(rule.getRuleName(), rule));
    }

    /**
     * 执行规则加载解析
     * 
     * @param ruleName 规则名称
     * @param clause 子句
     * @param params 参数
     * @return 解析后的子句
     */
    public Object load(String ruleName, Object clause, Map<String, Object> params) {
        if (StringUtils.isBlank(ruleName) || Objects.isNull(clause)) {
            return null;
        }
        IRule rule = ruleMap.get(ruleName);
        if (Objects.isNull(rule)) {
            throw new TechRecommendException(ErrorCodeEnum.DSL_BUILD_ERROR);
        }
        return rule.parse(clause, params);
    }

}

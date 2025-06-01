package com.tech.recommend.service.dsl.rule;

import java.util.Map;

/**
 * 规则定义接口层
 *
 * @author winding bubu
 * @since 2025/05/25
 */
public interface IRule {

    /**
     * 子句解析
     * 
     * @param clause 子句
     * @param params 入参
     * @return 解析后的子句
     */
    Object parse(Object clause, Map<String, Object> params);

    /**
     * rule 名称获取
     * 
     * @return ruleName
     */
    String getRuleName();

}

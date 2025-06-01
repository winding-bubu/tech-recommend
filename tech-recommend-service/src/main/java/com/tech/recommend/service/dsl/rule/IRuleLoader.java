package com.tech.recommend.service.dsl.rule;

import java.util.Map;

/**
 * 规则加载接口层
 *
 * @author winding bubu
 * @since 2025/06/01
 */
public interface IRuleLoader {

    /**
     * 加载执行
     * 
     * @param ruleName 规则名称
     * @param clause 原始子句
     * @param params 参数
     * @return 解析后的子句
     */
    Object load(String ruleName, Object clause, Map<String, Object> params);

}

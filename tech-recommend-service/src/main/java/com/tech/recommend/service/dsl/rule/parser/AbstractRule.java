package com.tech.recommend.service.dsl.rule.parser;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.common.util.TypeUtil;
import com.tech.recommend.service.dsl.rule.IRule;
import com.tech.recommend.service.dsl.rule.RuleLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 规则执行抽象层
 *
 * @author winding bubu
 * @since 2025/05/25
 */
@Slf4j
public abstract class AbstractRule implements IRule {
    
    @Resource
    protected RuleLoader ruleLoader;
    
    @Override
    public Object parse(Object clause, Map<String, Object> params) {
        try {
            return this.doParse(clause, params);
        } catch (Exception e) {
            log.error("rule parse occur error, rule:{}, clause:{}, error:", getRuleName(), clause.toString(), e);
            throw new TechRecommendException(ErrorCodeEnum.RULE_PARSE_ERROR);
        }
    }

    /**
     * 抽象方法执行解析
     * 
     * @param clause 子句
     * @param params 参数
     * @return 解析后子句
     */
    protected abstract Object doParse(Object clause, Map<String, Object> params);

    /**
     * 判断是否为JsonObject格式
     * 
     * @param clause 子句
     * @return true:格式不符
     */
    protected boolean notJsonObject(Object clause) {
        return Objects.isNull(clause) || !(clause instanceof JSONObject);
    }

    /**
     * 判断是否为JsonArray格式
     * 
     * @param clause 子句
     * @return true:格式不符
     */
    protected boolean notJsonArray(Object clause) {
        return Objects.isNull(clause) || !(clause instanceof JSONArray);
    }

    /**
     * must、must_not、should 等分子规则解析
     * 
     * @param clause 分子子句
     * @param params 参数
     * @return 解析后的子句
     */
    protected JSONArray parseArrayClause(JSONArray clause, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(clause)) {
            return null;
        }
        JSONArray clauseAdjust = new JSONArray();
        for (Object itemClause : clause) {
            if (this.notJsonObject(itemClause)) {
                continue;
            }
            JSONObject itemClauseAdjust = new JSONObject();
            for (Map.Entry<String, Object> entry : ((JSONObject)itemClause).entrySet()) {
                Object result = ruleLoader.load(entry.getKey(), entry.getValue(), params);
                if (Objects.nonNull(result)) {
                    itemClauseAdjust.put(entry.getKey(), result);
                }
            }
            clauseAdjust.add(itemClauseAdjust);
        }
        return clauseAdjust;
    }

    /**
     * term、terms 等原子规则解析
     * 
     * @param clause 原子子句
     * @param params 参数
     * @return 解析后的子句
     */
    protected JSONObject parseObjectClause(JSONObject clause, Map<String, Object> params) {
        if (MapUtils.isEmpty(clause)) {
            return null;
        }
        JSONObject clauseAdjust = new JSONObject();
        for (Map.Entry<String, Object> entry : clause.entrySet()) {
            Object paramValue = this.getParamValue(entry.getValue(), params);
            if (Objects.nonNull(paramValue)) {
                clauseAdjust.put(entry.getKey(), paramValue);
            }
        }
        return clauseAdjust;
    }

    private Object getParamValue(Object value, Map<String, Object> params) {
        if (!(value instanceof String)) {
            return value;
        }
        String valueStr = (String)value;
        if (valueStr.startsWith("#")) {
            String paramKey = valueStr.substring(1);
            return params.get(paramKey);
        }
        return null;
    }

}

package com.tech.recommend.service.dsl.rule.parser;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.service.dsl.rule.IRule;
import com.tech.recommend.service.dsl.rule.IRuleLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

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

    protected IRuleLoader ruleLoader;
    
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
    protected JSONArray parseJsonArrayClause(JSONArray clause, Map<String, Object> params) {
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
     * term、terms、exists 等原子规则解析
     * 
     * @param clause 原子子句
     * @param params 参数
     * @return 解析后的子句
     */
    protected JSONObject parseJsonObjectClause(JSONObject clause, Map<String, Object> params) {
        if (MapUtils.isEmpty(clause)) {
            return null;
        }
        JSONObject clauseAdjust = new JSONObject();
        for (Map.Entry<String, Object> entry : clause.entrySet()) {
            String param = entry.getKey();
            Object value = entry.getValue();
            Object parsedValue;
            if (value instanceof String) {
                parsedValue = this.parseStringValue((String)value, params);
            } else if (value instanceof JSONObject) {
                parsedValue = this.parseJSONObjectValue((JSONObject)value, params);
            } else {
                parsedValue = value;
            }
            if (Objects.nonNull(parsedValue)) {
                clauseAdjust.put(param, parsedValue);
            }
        }
        return clauseAdjust;
    }
    
    private Object parseStringValue(String value, Map<String, Object> params) {
        // 带#的为未知参数，需要对应的取出来，否则直接返回
        if (value.startsWith("#")) {
            String paramKey = value.substring(1);
            return params.get(paramKey);
        }
        return value;
    }

    private Object parseJSONObjectValue(JSONObject value, Map<String, Object> params) {
        JSONObject parsedValue = new JSONObject();
        // 再进行遍历
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            if (entry.getValue() instanceof String) {
                parsedValue.put(entry.getKey(), this.parseStringValue(entry.getValue().toString(), params));
            } else {
                parsedValue.put(entry.getKey(), entry.getValue());
            }
        }
        return parsedValue;
    }

}

package com.tech.recommend.service.dsl.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.configuration.config.DslConfig;
import com.tech.recommend.common.constant.DslBuildEnum;
import com.tech.recommend.domain.api.model.dsl.DslBuildRequest;
import com.tech.recommend.domain.api.model.dsl.DslBuildResponse;
import com.tech.recommend.domain.api.service.IDslBuildProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * dsl正向构建
 *
 * @author winding bubu
 * @since 2025/05/18
 */
@Slf4j
@Component
public class ForwardDslBuilder implements IDslBuildProcessor {

    @Override
    public DslBuildResponse build(DslBuildRequest dslBuildRequest) {

        // 获取dsl模板
        String dslTemplate = Optional.ofNullable(dslBuildRequest.getDslConfig())
                .map(DslConfig::getDsl)
                .orElse(null);
        if (StringUtils.isBlank(dslTemplate)) {
            return null;
        }

        // dsl构建
        String dsl = this.buildDsl(dslTemplate, dslBuildRequest.getParams());
        if (StringUtils.isBlank(dsl)) {
            return null;
        }

        // 结果返回
        DslBuildResponse dslBuildResponse = new DslBuildResponse();
        dslBuildResponse.setDsl(dsl);
        return dslBuildResponse;
    }

    private String buildDsl(String dslTemplate, Map<String, Object> params) {

        // 获取 filter 子句
        JSONObject root = JSON.parseObject(dslTemplate);
        JSONArray filter = Optional.ofNullable(root)
                .map(item -> item.getJSONObject("query"))
                .map(item -> item.getJSONObject("bool"))
                .map(item -> item.getJSONArray("filter"))
                .orElse(null);
        if (CollectionUtils.isEmpty(filter)) {
            return null;
        }

        // 动态处理 filter 子句
        JSONArray parsedFilter = this.parseJsonArrayClause(filter, params);
        if (CollectionUtils.isEmpty(parsedFilter)) {
            return null;
        }
        
        // 结果处理生成
        return this.buildParsedRoot(parsedFilter, root).toString();
    }

    private JSONArray parseJsonArrayClause(JSONArray clause, Map<String, Object> params) {

        JSONArray parsedResult = new JSONArray();

        for (Object clauseObj : clause) {
            if (!(clauseObj instanceof JSONObject)) {
                continue;
            }
            JSONObject parsedClause = this.parseJsonObjectClause((JSONObject)clauseObj, params);
            if (MapUtils.isNotEmpty(parsedClause)) {
                parsedResult.add(parsedClause);
            }
        }
        
        if (CollectionUtils.isEmpty(parsedResult)) {
            return null;
        }

        return parsedResult;
    }

    private JSONObject parseJsonObjectClause(JSONObject clause, Map<String, Object> params) {

        JSONObject parsedResult = new JSONObject();

        for (Map.Entry<String, Object> entry : clause.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isBlank(key) || Objects.isNull(value)) {
                continue;
            }
            Object parsedValue;
            if (value instanceof String) {
                parsedValue = this.parsedStringValue((String)value, params);
            } else if (value instanceof JSONObject) {
                parsedValue = this.parseJsonObjectClause((JSONObject)value, params);
            } else if (value instanceof JSONArray) {
                parsedValue = this.parseJsonArrayClause((JSONArray)value, params);
            } else {
                parsedValue = value;
            }
            if (Objects.nonNull(parsedValue)) {
                parsedResult.put(key, parsedValue);
            }
        }

        if (MapUtils.isEmpty(parsedResult)) {
            return null;
        }

        return parsedResult;
    }

    private Object parsedStringValue(String value, Map<String, Object> params) {
        if (value.startsWith("#")) {
            String paramKey = value.substring(1);
            return params.get(paramKey);
        }
        return value;
    }
    
    private JSONObject buildParsedRoot(JSONArray parsedFilter, JSONObject originRoot) {

        JSONObject filter = new JSONObject();
        filter.put("filter", parsedFilter);

        JSONObject bool = new JSONObject();
        bool.put("bool", filter);

        JSONObject parsedRoot = new JSONObject();
        parsedRoot.put("query", bool);

        for (Map.Entry<String, Object> entry : originRoot.entrySet()) {
            if ("query".equals(entry.getKey())) {
                continue;
            }
            parsedRoot.put(entry.getKey(), entry.getValue());
        }

        log.info("dsl print: {}", JSON.toJSONString(parsedRoot));
        return parsedRoot;
    }

    @Override
    public String type() {
        return DslBuildEnum.FROWARD.getCode();
    }

}

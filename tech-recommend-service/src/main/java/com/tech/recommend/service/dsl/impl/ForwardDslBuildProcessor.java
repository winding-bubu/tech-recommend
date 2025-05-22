package com.tech.recommend.service.dsl.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.configuration.config.DslConfig;
import com.tech.recommend.common.constant.DslBuildEnum;
import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
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

import static com.tech.recommend.common.constant.EsRuleEnum.*;

/**
 * dsl正向构建
 *
 * @author winding bubu
 * @since 2025/05/18
 */
@Slf4j
@Component
public class ForwardDslBuildProcessor implements IDslBuildProcessor {

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
        try {
            JSONObject root = JSON.parseObject(dslTemplate);
            JSONArray filter = Optional.ofNullable(root)
                    .map(item -> item.getJSONObject("query"))
                    .map(item -> item.getJSONObject("bool"))
                    .map(item -> item.getJSONArray("filter"))
                    .orElse(null);
            if (CollectionUtils.isEmpty(filter)) {
                return null;
            }

            // 动态处理filter子句
            JSONArray filterAdjust = new JSONArray();
            for (Object item : filter) {
                if (!(item instanceof JSONObject)) {
                    continue;
                }
                JSONObject clause = (JSONObject) item;
                JSONObject clauseAdjust = this.processClause(clause, params);
                if (Objects.nonNull(clauseAdjust)) {
                    filterAdjust.add(clauseAdjust);
                }
            }
            if (CollectionUtils.isEmpty(filterAdjust)) {
                return null;
            }

            return root.toString();
        } catch (Exception e) {
            log.error("forward dsl build error", e);
            // 抛出自定义异常
            throw new TechRecommendException(ErrorCodeEnum.DSL_BUILD_ERROR);
        }
    }

    private JSONObject processClause(JSONObject clause, Map<String, Object> params) {

        // dsl语句特性，只获取第一个即可
        String rule = clause.keySet().iterator().next();
        Object valueObj = clause.get(rule);

        // bool
        if (BOOL.isMatch(rule)) {
            return this.processBool((JSONObject)valueObj, params);
        }
        
        // should
        if (SHOULD.isMatch(rule)) {
            return this.processShould((JSONArray)valueObj, params);
        }
        
        // must
        
        // must_not

        return null;
    }

    private JSONObject processBool(JSONObject clause, Map<String, Object> params) {
        JSONObject boolValue = this.processClause(clause, params);
        if (MapUtils.isEmpty(boolValue)) {
            return null;
        }
        JSONObject clauseAdjust = new JSONObject();
        clauseAdjust.put("bool", boolValue);
        return clauseAdjust;
    }

    private JSONObject processShould(JSONArray clause, Map<String, Object> params) {

        JSONArray shouldAdjust = new JSONArray();
        for (Object item : clause) {
            if (!(item instanceof JSONObject)) {
                continue;
            }
            JSONObject itemAdjust = this.processClause((JSONObject) item, params);
            if (Objects.nonNull(itemAdjust)) {
                shouldAdjust.add(itemAdjust);
            }
        }
        if (CollectionUtils.isEmpty(shouldAdjust)) {
            return null;
        }
        JSONObject clauseAdjust = new JSONObject();
        clauseAdjust.put("should", shouldAdjust);
        return clauseAdjust;
    }

    @Override
    public String type() {
        return DslBuildEnum.FROWARD.getCode();
    }

}

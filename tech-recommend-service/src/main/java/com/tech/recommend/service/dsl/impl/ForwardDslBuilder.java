package com.tech.recommend.service.dsl.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.configuration.config.DslConfig;
import com.tech.recommend.common.constant.DslBuildEnum;
import com.tech.recommend.domain.api.model.dsl.DslBuildRequest;
import com.tech.recommend.domain.api.model.dsl.DslBuildResponse;
import com.tech.recommend.domain.api.service.IDslBuildProcessor;
import com.tech.recommend.service.dsl.rule.IRuleLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource(name = "parentRuleLoader")
    private IRuleLoader parentRuleLoader;

    @Override
    public DslBuildResponse build(DslBuildRequest dslBuildRequest) {

        // 获取dsl模板
        String dslTemplate = Optional.ofNullable(dslBuildRequest.getDslConfig()).map(DslConfig::getDsl).orElse(null);
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
        JSONObject root = JSON.parseObject(dslTemplate);
        JSONArray filter = Optional.ofNullable(root).map(item -> item.getJSONObject("query")).map(item -> item.getJSONObject("bool"))
            .map(item -> item.getJSONArray("filter")).orElse(null);
        if (CollectionUtils.isEmpty(filter)) {
            return null;
        }

        // 动态处理filter子句
        JSONArray parsedFilter = new JSONArray();
        for (Object item : filter) {
            if (!(item instanceof JSONObject)) {
                continue;
            }
            JSONObject clause = (JSONObject)item;
            JSONObject parsedClause = (JSONObject)item;
            for (Map.Entry<String, Object> entry : clause.entrySet()) {
                Object parsedRuleClause = parentRuleLoader.load(entry.getKey(), entry.getValue(), params);
                if (Objects.nonNull(parsedRuleClause)) {
                    parsedClause.put(entry.getKey(), parsedRuleClause);
                    parsedFilter.add(parsedRuleClause);
                }
            }
        }
        if (CollectionUtils.isEmpty(parsedFilter)) {
            return null;
        }
        
        // 构建解析后的dsl
        return this.buildParsedRoot(parsedFilter).toString();
    }

    private JSONObject buildParsedRoot(JSONArray parsedFilter) {

        JSONObject filter = new JSONObject();
        filter.put("filter", parsedFilter);

        JSONObject bool = new JSONObject();
        bool.put("bool", filter);

        JSONObject root = new JSONObject();
        root.put("query", bool);

        log.info("dsl print: {}", JSON.toJSONString(root));
        return root;
    }

    @Override
    public String type() {
        return DslBuildEnum.FROWARD.getCode();
    }

}

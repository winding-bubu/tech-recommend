package com.tech.recommend.service.dsl.impl;

import com.tech.recommend.common.configuration.config.DslConfig;
import com.tech.recommend.common.constant.DslBuildEnum;
import com.tech.recommend.domain.api.model.dsl.DslBuildRequest;
import com.tech.recommend.domain.api.model.dsl.DslBuildResponse;
import com.tech.recommend.domain.api.service.IDslBuildProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

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
        return null;
    }

    @Override
    public String type() {
        return DslBuildEnum.FROWARD.getCode();
    }

}

package com.tech.recommend.service.dsl.impl;

import org.springframework.stereotype.Component;

import com.tech.recommend.common.constant.DslBuildEnum;
import com.tech.recommend.domain.api.model.dsl.DslBuildRequest;
import com.tech.recommend.domain.api.model.dsl.DslBuildResponse;
import com.tech.recommend.domain.api.service.IDslBuildProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * dsl反向组合
 *
 * @author winding bubu
 * @since 2025/05/18
 */
@Slf4j
@Component
public class BackwardDslBuildProcessor implements IDslBuildProcessor {

    @Override
    public DslBuildResponse build(DslBuildRequest dslBuildRequest) {
        return null;
    }

    @Override
    public String type() {
        return DslBuildEnum.FROWARD.getCode();
    }

}

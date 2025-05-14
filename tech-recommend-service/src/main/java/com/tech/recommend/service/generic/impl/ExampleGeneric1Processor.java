package com.tech.recommend.service.generic.impl;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.domain.api.context.GenericContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * 示例泛化1
 *
 * @author winding bubu
 * @since 2025/05/06
 */
@Component
public class ExampleGeneric1Processor extends AbstractGenericProcessor {

    @Override
    protected Map<String, Object> execute(GenericConfig genericConfig, GenericContext genericContext) {
        return Collections.emptyMap();
    }

    @Override
    public String genericId() {
        return "exampleGeneric1";
    }

}

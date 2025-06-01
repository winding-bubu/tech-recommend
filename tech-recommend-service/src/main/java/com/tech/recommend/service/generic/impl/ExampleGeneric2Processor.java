package com.tech.recommend.service.generic.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.domain.api.context.GenericContext;

/**
 * 示例泛化2
 *
 * @author winding bubu
 * @since 2025/05/06
 */
@Component
public class ExampleGeneric2Processor extends AbstractGenericProcessor {

    @Override
    protected Map<String, Object> execute(GenericConfig genericConfig, GenericContext genericContext) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("uid", 1000000010);
        return map;
    }

    @Override
    public String genericId() {
        return "exampleGeneric2";
    }

}

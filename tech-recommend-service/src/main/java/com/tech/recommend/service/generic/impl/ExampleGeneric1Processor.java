package com.tech.recommend.service.generic.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.domain.api.context.GenericContext;
import org.springframework.stereotype.Component;

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
        Map<String, Object> map = Maps.newHashMap();
        map.put("department", Lists.newArrayList("devops", "sre"));
        map.put("status", 1);
        return map;
    }

    @Override
    public String genericId() {
        return "exampleGeneric1";
    }

}

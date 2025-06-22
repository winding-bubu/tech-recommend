package com.tech.recommend.service.generic.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.domain.api.context.GenericContext;

/**
 * 性别泛化
 *
 * @author winding bubu
 * @since 2025/06/22
 */
@Component
public class GenderGeneric extends AbstractGenericProcessor {

    @Override
    protected Map<String, Object> execute(GenericConfig genericConfig, GenericContext genericContext) {
        Map<String, Object> result = new HashMap<>();
        // 性别限定：男 1、女 2
        result.put("gender", Lists.newArrayList(1, 2));
        return result;
    }

    @Override
    public String genericId() {
        return "genderGeneric";
    }

}

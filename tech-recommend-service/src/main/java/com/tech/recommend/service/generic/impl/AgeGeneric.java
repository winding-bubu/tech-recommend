package com.tech.recommend.service.generic.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.domain.api.context.GenericContext;

/**
 * 年龄范围泛化
 *
 * @author winding bubu
 * @since 2025/06/22
 */
@Component
public class AgeGeneric extends AbstractGenericProcessor {

    @Override
    protected Map<String, Object> execute(GenericConfig genericConfig, GenericContext genericContext) {
        Map<String, Object> result = new HashMap<>();
        // 年龄限定 10-30 岁
        JSONObject ageRange = new JSONObject();
        ageRange.put("gte", 10);
        ageRange.put("lte", 30);
        result.put("ageRange", ageRange);
        return result;
    }

    @Override
    public String genericId() {
        return "ageGeneric";
    }

}

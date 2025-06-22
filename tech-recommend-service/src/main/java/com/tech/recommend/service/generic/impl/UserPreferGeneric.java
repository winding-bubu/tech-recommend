package com.tech.recommend.service.generic.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.domain.api.context.GenericContext;

/**
 * 用户偏好泛化
 *
 * @author winding bubu
 * @since 2025/06/22
 */
@Component
public class UserPreferGeneric extends AbstractGenericProcessor {

    @Override
    protected Map<String, Object> execute(GenericConfig genericConfig, GenericContext genericContext) {
        Map<String, Object> result = new HashMap<>();
        // 构建用户标签
        result.put("userTags", Lists.newArrayList("programmer", "worker", "student"));
        // 构建偏好
        result.put("preference", Lists.newArrayList("knowledge", "entertainment"));
        return result;
    }

    @Override
    public String genericId() {
        return "userPreferGeneric";
    }

}

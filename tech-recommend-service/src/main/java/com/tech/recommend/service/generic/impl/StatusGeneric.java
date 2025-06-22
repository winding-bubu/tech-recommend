package com.tech.recommend.service.generic.impl;

import com.google.common.collect.Lists;
import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.domain.api.context.GenericContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态泛化
 *
 * @author winding bubu
 * @since 2025/06/22
 */
@Component
public class StatusGeneric extends AbstractGenericProcessor {

    @Override
    protected Map<String, Object> execute(GenericConfig genericConfig, GenericContext genericContext) {
        Map<String, Object> result = new HashMap<>();
        // 状态限定: 有效 1
        result.put("status", Lists.newArrayList(1));
        return result;
    }

    @Override
    public String genericId() {
        return "statusGeneric";
    }

}

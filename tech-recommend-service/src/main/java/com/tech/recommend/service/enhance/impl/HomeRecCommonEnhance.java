package com.tech.recommend.service.enhance.impl;

import com.tech.recommend.common.configuration.config.EnhanceConfig;
import com.tech.recommend.domain.api.context.EnhanceContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 首页推荐通用过滤
 *
 * @author winding bubu
 * @since 2025/06/22
 */
@Component
public class HomeRecCommonEnhance extends AbstractEnhanceProcessor {

    @Override
    protected void execute(EnhanceConfig enhanceConfig, EnhanceContext enhanceContext) {
        if (CollectionUtils.isEmpty(enhanceContext.getResultItems())) {
            return;
        }
        enhanceContext.getResultItems().removeIf(item -> {
            if (Objects.isNull(item)) {
                return true;
            }
            // 过滤ID或者召回信息不存在的item
            if (StringUtils.isBlank(item.getItemId()) || CollectionUtils.isEmpty(item.getRecallTags())) {
                return true;
            }
            return false;
        });
    }

    @Override
    public String enhanceId() {
        return "homeRecCommonEnhance";
    }

}

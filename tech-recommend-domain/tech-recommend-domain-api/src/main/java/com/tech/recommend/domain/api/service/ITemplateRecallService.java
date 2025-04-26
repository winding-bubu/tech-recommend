package com.tech.recommend.domain.api.service;

import com.tech.recommend.domain.api.context.TemplateContext;

/**
 * 模板维度召回执行
 *
 * @author winding bubu
 * @since 2025/04/24
 */
public interface ITemplateRecallService {

    /**
     * 模板召回
     *
     * @param templateContext 模板上下文
     */
    void recall(TemplateContext templateContext);

}

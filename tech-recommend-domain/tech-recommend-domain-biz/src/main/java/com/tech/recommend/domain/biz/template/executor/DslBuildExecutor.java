package com.tech.recommend.domain.biz.template.executor;

import com.tech.recommend.domain.api.context.TemplateContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * dsl语句组装执行
 *
 * @author winding bubu
 * @since 2025/05/17
 */
@Slf4j
@Component
public class DslBuildExecutor {

    /**
     * 构建dsl
     * 
     * @param dslTemplate dsl模板
     * @param templateContext 模板上下文
     * @return dsl
     */
    public String buildDsl(String dslTemplate, TemplateContext templateContext) {
        return null;
    }

}

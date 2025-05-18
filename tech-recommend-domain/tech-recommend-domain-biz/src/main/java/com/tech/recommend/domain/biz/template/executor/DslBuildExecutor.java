package com.tech.recommend.domain.biz.template.executor;

import com.tech.recommend.common.configuration.config.DslConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationLoader;
import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.domain.api.context.TemplateContext;
import com.tech.recommend.domain.api.model.dsl.DslBuildRequest;
import com.tech.recommend.domain.api.model.dsl.DslBuildResponse;
import com.tech.recommend.domain.api.service.IDslBuildProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * dsl语句组装执行
 *
 * @author winding bubu
 * @since 2025/05/17
 */
@Slf4j
@Component
public class DslBuildExecutor {

    @Resource
    private ConfigurationLoader configurationLoader;

    private final Map<String, IDslBuildProcessor> dslBuildProcessorMap = new HashMap<>();

    public DslBuildExecutor(List<IDslBuildProcessor> dslBuildProcessors) {
        dslBuildProcessors.forEach(dslBuildProcessor -> dslBuildProcessorMap.put(dslBuildProcessor.type(), dslBuildProcessor));
    }

    /**
     * 构建dsl
     * 
     * @param templateContext 模板上下文
     * @return dsl
     */
    public String buildDsl(TemplateContext templateContext) {

        // 获取 dsl 配置
        DslConfig dslConfig = configurationLoader.getTemplateDslConfig(templateContext.getSceneId(), templateContext.getTemplateId());
        if (Objects.isNull(dslConfig) || StringUtils.isBlank(dslConfig.getType())) {
            throw new TechRecommendException(ErrorCodeEnum.DSL_LACK);
        }

        // 根据类型加载不同的构建策略
        IDslBuildProcessor dslBuildProcessor = dslBuildProcessorMap.get(dslConfig.getType());
        if (Objects.isNull(dslBuildProcessor)) {
            return null;
        }

        // 构建请求
        DslBuildRequest dslBuildRequest = new DslBuildRequest();
        dslBuildRequest.setDslConfig(dslConfig);
        dslBuildRequest.setParams(templateContext.cloneParams());

        // 执行构建
        DslBuildResponse dslBuildResponse = dslBuildProcessor.build(dslBuildRequest);
        if (Objects.isNull(dslBuildResponse)) {
            return null;
        }
        return dslBuildResponse.getDsl();
    }

}

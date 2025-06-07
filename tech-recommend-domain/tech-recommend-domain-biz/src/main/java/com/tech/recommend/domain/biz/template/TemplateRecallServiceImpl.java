package com.tech.recommend.domain.biz.template;

import com.alibaba.fastjson2.JSON;
import com.tech.recommend.common.configuration.config.IndexConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationLoader;
import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.constant.ResourceIdEnum;
import com.tech.recommend.common.constant.ResourceTypeEnum;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.common.resource.ResourceLoader;
import com.tech.recommend.common.resource.model.ResourceContext;
import com.tech.recommend.common.resource.model.ResourceResponse;
import com.tech.recommend.domain.api.context.TemplateContext;
import com.tech.recommend.domain.api.biz.ITemplateRecallService;
import com.tech.recommend.domain.biz.template.executor.DslBuildExecutor;
import com.tech.recommend.facade.model.ResultItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 模板召回上下文
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@Slf4j
@Service
public class TemplateRecallServiceImpl implements ITemplateRecallService {

    @Resource
    private ResourceLoader resourceLoader;

    @Resource
    private ConfigurationLoader configurationLoader;

    @Resource
    private DslBuildExecutor dslBuildExecutor;

    @Override
    public void recall(TemplateContext templateContext) {
        try {
            // 获取索引
            IndexConfig indexConfig =
                configurationLoader.getTemplateIndexConfig(templateContext.getSceneId(), templateContext.getTemplateId());
            if (Objects.isNull(indexConfig)) {
                throw new TechRecommendException(ErrorCodeEnum.INDEX_LACK);
            }
            // 执行查询
            if (indexConfig.isEsType()) {
                this.esQuery(indexConfig, templateContext);
            } else {
                this.rpcQuery(indexConfig, templateContext);
            }
        } catch (TechRecommendException te) {
            log.error("template recall custom error", te);
        } catch (Exception e) {
            log.error("template recall occur error", e);
        }
    }

    private void esQuery(IndexConfig indexConfig, TemplateContext templateContext) {

        // 构建dsl
        String dsl = dslBuildExecutor.buildDsl(templateContext);
        if (StringUtils.isBlank(dsl)) {
            log.error(JSON.toJSONString(templateContext));
            throw new TechRecommendException(ErrorCodeEnum.DSL_BUILD_ERROR);
        }

        // 构建上下文
        ResourceContext resourceContext = new ResourceContext();
        resourceContext.setResourceType(ResourceTypeEnum.ES.getCode());
        resourceContext.setResourceId(ResourceIdEnum.ES.getCode());
        resourceContext.putParam("dsl", dsl);
        resourceContext.putParam("index", indexConfig.getIndexId());

        // 查询
        ResourceResponse resourceResponse = resourceLoader.resourceLoad(resourceContext);
        if (Objects.nonNull(resourceResponse) && Objects.nonNull(resourceResponse.getResourceResult())) {
            List<ResultItem> resultItems = (List<ResultItem>)resourceResponse.getResourceResult();
            templateContext.setResultItems(resultItems);
        }
    }

    private void rpcQuery(IndexConfig indexConfig, TemplateContext templateContext) {

        // 构建上下文
        ResourceContext resourceContext = new ResourceContext();
        resourceContext.setResourceType(ResourceTypeEnum.RPC.getCode());
        resourceContext.setResourceId(indexConfig.getIndexId());
        resourceContext.setParams(templateContext.cloneParams());

        // 资源查询
        ResourceResponse resourceResponse = resourceLoader.resourceLoad(resourceContext);
        if (Objects.nonNull(resourceResponse) && Objects.nonNull(resourceResponse.getResourceResult())) {
            List<ResultItem> resultItems = (List<ResultItem>)resourceResponse.getResourceResult();
            templateContext.setResultItems(resultItems);
        }
    }

}

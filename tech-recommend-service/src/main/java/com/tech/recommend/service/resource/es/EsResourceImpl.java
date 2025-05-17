package com.tech.recommend.service.resource.es;

import com.google.common.collect.Lists;
import com.tech.recommend.common.constant.ResourceTypeEnum;
import com.tech.recommend.common.resource.model.ResourceContext;
import com.tech.recommend.common.resource.model.ResourceResponse;
import com.tech.recommend.common.resource.IResourceProcessor;
import com.tech.recommend.service.repo.es.ElasticsearchRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * es 资源加载实现类
 *
 * @author winding bubu
 * @since 2025/05/15
 */
@Slf4j
@Component
public class EsResourceImpl implements IResourceProcessor {

    @Resource
    private ElasticsearchRepo elasticsearchRepo;

    @Override
    public List<ResourceResponse> resourcesLoad(List<ResourceContext> resourceContexts) {
        if (CollectionUtils.isEmpty(resourceContexts) || Objects.isNull(resourceContexts.get(0))) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(this.resourceLoad(resourceContexts.get(0)));
    }

    private ResourceResponse resourceLoad(ResourceContext resourceContext) {
        try {
            // 索引与dsl获取
            String index = resourceContext.getParam("index");
            String dsl = resourceContext.getParam("dsl");
            if (StringUtils.isBlank(index) || StringUtils.isBlank(dsl)) {
                return ResourceResponse.buildEmptyResponse(resourceContext);
            }
            // 查询es
            return ResourceResponse.buildResponse(resourceContext, elasticsearchRepo.search(index, dsl));
        } catch (Exception e) {
            log.error("es resource load error", e);
            return ResourceResponse.buildEmptyResponse(resourceContext);
        }
    }

    @Override
    public String resourceType() {
        return ResourceTypeEnum.ES.getCode();
    }

}

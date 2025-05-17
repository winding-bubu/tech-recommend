package com.tech.recommend.common.resource;

import com.tech.recommend.common.resource.model.ResourceContext;
import com.tech.recommend.common.resource.model.ResourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 资源加载类
 *
 * @author winding bubu
 * @since 2025/05/17
 */
@Slf4j
@Component
public class ResourceLoader {

    private final Map<String, IResourceProcessor> resourceProcessors = new HashMap<>();

    public ResourceLoader(List<IResourceProcessor> resourceProcessors) {
        resourceProcessors.forEach(resourceProcessor -> this.resourceProcessors.put(resourceProcessor.resourceType(), resourceProcessor));
    }

    /**
     * 单一资源加载
     * 
     * @param resourceContext 资源上下文
     * @return 资源结果
     */
    public ResourceResponse resourceLoad(ResourceContext resourceContext) {
        if (Objects.isNull(resourceContext) || StringUtils.isBlank(resourceContext.getResourceType())) {
            return ResourceResponse.buildEmptyResponse(null, null);
        }

        String type = resourceContext.getResourceType();
        String id = resourceContext.getResourceId();

        return Optional.ofNullable(resourceProcessors.get(type))
                .map(processor -> processor.resourcesLoad(Collections.singletonList(resourceContext)))
                .filter(CollectionUtils::isNotEmpty)
                .map(responses -> responses.get(0))
                .orElseGet(() -> ResourceResponse.buildEmptyResponse(resourceContext));
    }

}

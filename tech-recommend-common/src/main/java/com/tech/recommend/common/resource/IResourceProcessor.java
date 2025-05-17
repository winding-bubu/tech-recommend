package com.tech.recommend.common.resource;

import com.tech.recommend.common.resource.model.ResourceContext;
import com.tech.recommend.common.resource.model.ResourceResponse;

import java.util.List;

/**
 * 召回执行
 *
 * @author winding bubu
 * @since 2025/05/15
 */
public interface IResourceProcessor {

    /**
     * 单一资源类型，批量资源加载
     * 
     * @param resourceContexts 资源上下文
     */
    List<ResourceResponse> resourcesLoad(List<ResourceContext> resourceContexts);

    /**
     * 资源类型
     * 
     * @see com.tech.recommend.common.constant.ResourceTypeEnum
     * 
     * @return 类型
     */
    String resourceType();

}

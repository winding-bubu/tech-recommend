package com.tech.recommend.common.resource.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源返回结果
 *
 * @author winding bubu
 * @since 2025/05/17
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResourceResponse {

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 资源ID
     */
    private String resourceId;

    /**
     * 资源结果
     */
    private Object resourceResult;


    public static ResourceResponse buildEmptyResponse(String resourceType, String resourceId) {
        return ResourceResponse.builder()
                .resourceType(resourceType)
                .resourceId(resourceId)
                .build();
    }

    public static ResourceResponse buildEmptyResponse(ResourceContext resourceContext) {
        return ResourceResponse.builder()
                .resourceType(resourceContext.getResourceType())
                .resourceId(resourceContext.getResourceId())
                .build();
    }

    public static ResourceResponse buildResponse(ResourceContext resourceContext, Object resourceResult) {
        return ResourceResponse.builder()
                .resourceType(resourceContext.getResourceType())
                .resourceId(resourceContext.getResourceId())
                .resourceResult(resourceResult)
                .build();
    }

}

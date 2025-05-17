package com.tech.recommend.service.resource.rpc;

import com.tech.recommend.common.constant.ResourceTypeEnum;
import com.tech.recommend.common.resource.model.ResourceContext;
import com.tech.recommend.common.resource.model.ResourceResponse;
import com.tech.recommend.common.resource.IResourceProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * rpc资源加载
 *
 * @author winding bubu
 * @since 2025/05/15
 */
@Slf4j
@Component
public class RpcResourceImpl implements IResourceProcessor {

    @Override
    public List<ResourceResponse> resourcesLoad(List<ResourceContext> resourceContexts) {
        return new ArrayList<>();
    }

    @Override
    public String resourceType() {
        return ResourceTypeEnum.RPC.getCode();
    }

}

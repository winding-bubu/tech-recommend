package com.tech.recommend.domain.api.context;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * todo
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GenericContext extends BaseContext {

    /**
     * 泛化请求来源: scene:场景泛化请求、channel:渠道泛化请求
     */
    private String sourceType;

    /**
     * 泛化ID: 如果是场景来源，就是场景ID；如果是渠道来源，就是渠道ID，以此类推
     */
    private String sourceId;

    /**
     * 泛化策略ID列表
     */
    private List<String> strategyIds;

    public boolean isSceneGeneric() {
        return "scene".equals(sourceType);
    }

    public boolean isChannelGeneric() {
        return "channel".equals(sourceType);
    }

}

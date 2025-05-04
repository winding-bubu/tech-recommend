package com.tech.recommend.domain.api.context;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 泛化上下文
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@Data
public class GenericContext {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 泛化请求来源: scene:场景泛化请求、channel:渠道泛化请求
     */
    private String sourceType;

    /**
     * 场景ID
     */
    private String sceneId;

    /**
     * 渠道ID
     */
    private String channelId;

    /**
     * 泛化ID列表
     */
    private List<String> genericIds;

    /**
     * 召回基础参数
     */
    private Map<String, Object> params = new HashMap<>();

    public boolean isSceneGeneric() {
        return "scene".equals(sourceType);
    }

    public boolean isChannelGeneric() {
        return "channel".equals(sourceType);
    }

}

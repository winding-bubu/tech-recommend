package com.tech.recommend.domain.api.context;

import com.tech.recommend.facade.model.ResultItem;
import lombok.Data;

import java.util.List;

/**
 * 增强节点上下文
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Data
public class EnhanceContext {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 增强请求来源: scene:场景泛化请求、channel:渠道泛化请求
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
     * 增强ID列表
     */
    private List<String> enhanceIds;

    /**
     * 结果集合
     */
    private List<ResultItem> resultItems;

    public boolean isSceneEnhance() {
        return "scene".equals(sourceType);
    }

    public boolean isChannelEnhance() {
        return "channel".equals(sourceType);
    }

}

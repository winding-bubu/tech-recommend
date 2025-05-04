package com.tech.recommend.domain.api.context;

import com.tech.recommend.facade.model.ResultItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 增强节点上下文
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EnhanceContext extends BaseContext {

    /**
     * 增强请求来源: scene:场景泛化请求、channel:渠道泛化请求
     */
    private String sourceType;

    /**
     * 来源ID: 如果是场景来源，就是场景ID；如果是渠道来源，就是渠道ID，以此类推
     */
    private String sourceId;

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

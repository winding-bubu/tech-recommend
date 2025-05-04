package com.tech.recommend.domain.api.context;

import lombok.*;

import java.util.List;

/**
 * 泛化上下文
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
     * 泛化ID列表
     */
    private List<String> genericIds;

    public boolean isSceneGeneric() {
        return "scene".equals(sourceType);
    }

    public boolean isChannelGeneric() {
        return "channel".equals(sourceType);
    }

}

package com.tech.recommend.domain.api.context;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板上下文
 *
 * @author winding bubu
 * @since 2025/04/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateContext extends BaseContext {

    /**
     * 场景ID
     */
    private String sceneId;

    /**
     * 渠道ID
     */
    private String channelId;

    /**
     * 模板ID
     */
    private String templateId;

}

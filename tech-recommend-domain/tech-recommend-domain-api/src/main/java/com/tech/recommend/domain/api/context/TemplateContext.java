package com.tech.recommend.domain.api.context;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板上下文
 *
 * @author winding bubu
 * @since 2025/04/24
 */
@Data
public class TemplateContext {

    /**
     * 请求ID
     */
    private String requestId;

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

    /**
     * 召回基础参数
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * 参数弱拷贝
     *
     * @return 新集合
     */
    public Map<String, Object> cloneParams() {
        return new HashMap<>(params);
    }

}

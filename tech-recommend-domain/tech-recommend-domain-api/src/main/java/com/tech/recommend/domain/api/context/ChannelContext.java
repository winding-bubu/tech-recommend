package com.tech.recommend.domain.api.context;

import com.tech.recommend.facade.model.ResultItem;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道上下文
 *
 * @author winding bubu
 * @since 2025/04/24
 */
@Data
public class ChannelContext {

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
     * 超时时间
     */
    private Integer timeout;

    /**
     * 召回数量
     */
    private Integer recallNum;

    /**
     * 召回基础参数
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * 召回结果
     */
    private List<ResultItem> resultItems;

    /**
     * 参数弱拷贝
     *
     * @return 新集合
     */
    public Map<String, Object> cloneParams() {
        return new HashMap<>(params);
    }

}

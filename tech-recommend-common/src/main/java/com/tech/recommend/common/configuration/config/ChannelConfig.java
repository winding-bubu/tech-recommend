package com.tech.recommend.common.configuration.config;

import lombok.Data;

/**
 * 渠道配置
 *
 * @author winding bubu
 * @since 2025/05/03
 */
@Data
public class ChannelConfig {

    /**
     * 渠道ID
     */
    private String channelId;

    /**
     * 召回数量
     */
    private Integer recallNum;

    /**
     * 渠道召回超时时间
     */
    private Integer timeout;

    /**
     * 描述
     */
    private String description;

}

package com.tech.recommend.domain.api.service;

import com.tech.recommend.domain.api.context.ChannelContext;

/**
 * 渠道维度召回
 *
 * @author winding bubu
 * @since 2025/04/24
 */
public interface IChannelRecallService {

    /**
     * 召回执行
     *
     * @param channelContext 渠道上下文
     */
    void recall(ChannelContext channelContext);

}

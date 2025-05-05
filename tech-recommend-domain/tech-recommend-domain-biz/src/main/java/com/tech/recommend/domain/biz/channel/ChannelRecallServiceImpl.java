package com.tech.recommend.domain.biz.channel;

import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.domain.api.context.ChannelContext;
import com.tech.recommend.domain.api.biz.IChannelRecallService;
import com.tech.recommend.domain.biz.channel.executor.ChannelEnhanceExecutor;
import com.tech.recommend.domain.biz.channel.executor.ChannelGenericExecutor;
import com.tech.recommend.domain.biz.channel.executor.ChannelRecallExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 渠道召回上下文
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@Slf4j
@Component
public class ChannelRecallServiceImpl implements IChannelRecallService {

    @Resource
    private ChannelGenericExecutor channelGenericExecutor;

    @Resource
    private ChannelRecallExecutor channelRecallExecutor;

    @Resource
    private ChannelEnhanceExecutor channelEnhanceExecutor;

    @Override
    public void recall(ChannelContext channelContext) {
        try {
            // 执行渠道泛化
            channelGenericExecutor.generic(channelContext);
            // 执行渠道召回
            channelRecallExecutor.recall(channelContext);
            // 执行渠道增强
            channelEnhanceExecutor.enhance(channelContext);
        } catch (TechRecommendException te) {
            log.error("channel recall custom error", te);
        } catch (Exception e) {
            log.error("channel recall occur error", e);
        }
    }

}

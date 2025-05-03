package com.tech.recommend.domain.biz.scene;

import com.tech.recommend.domain.api.context.SceneContext;
import com.tech.recommend.domain.api.service.IChannelRecallService;
import com.tech.recommend.domain.api.service.ISceneRecallService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 场景召回实现
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@Component
public class SceneRecallServiceImpl implements ISceneRecallService {

    @Resource
    private IChannelRecallService channelRecallService;

    @Override
    public void recall(SceneContext sceneContext) {

        // 渠道上下文转换

        // 多线程执行渠道召回

    }

}

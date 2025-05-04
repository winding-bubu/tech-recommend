package com.tech.recommend.domain.biz.scene;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationFactory;
import com.tech.recommend.domain.api.context.SceneContext;
import com.tech.recommend.domain.api.biz.IChannelRecallService;
import com.tech.recommend.domain.api.biz.IGenericService;
import com.tech.recommend.domain.api.biz.ISceneRecallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 场景召回实现
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@Slf4j
@Component
public class SceneRecallServiceImpl implements ISceneRecallService {

    @Resource
    private IChannelRecallService channelRecallService;

    @Resource
    private IGenericService genericService;

    @Resource
    private ConfigurationFactory configurationFactory;

    @Override
    public void recall(SceneContext sceneContext) {
        try {
            // 执行场景泛化
            this.doSceneGeneric(sceneContext);
            // 执行召回
            this.doSceneRecall(sceneContext);
            // 执行场景增强
            this.doSceneEnhance(sceneContext);
        } catch (Exception e) {
            log.error("scene recall occur error", e);
        }
    }

    private void doSceneGeneric(SceneContext sceneContext) {

        // 获取场景泛化配置
        List<GenericConfig> sceneGenericConfigs = configurationFactory.getSceneGenericConfigs(sceneContext.getSceneId());


    }

    private void doSceneRecall(SceneContext sceneContext) {

    }

    private void doSceneEnhance(SceneContext sceneContext) {

    }

}

package com.tech.recommend.domain.biz.scene;

import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.domain.api.context.SceneContext;
import com.tech.recommend.domain.api.biz.ISceneRecallService;
import com.tech.recommend.domain.biz.scene.service.SceneEnhanceExecutor;
import com.tech.recommend.domain.biz.scene.service.SceneGenericExecutor;
import com.tech.recommend.domain.biz.scene.service.SceneRecallExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    private SceneGenericExecutor sceneGenericExecutor;

    @Resource
    private SceneRecallExecutor sceneRecallExecutor;

    @Resource
    private SceneEnhanceExecutor sceneEnhanceExecutor;

    @Override
    public void recall(SceneContext sceneContext) {
        try {
            // 执行场景泛化
            sceneGenericExecutor.generic(sceneContext);
            // 执行召回
            sceneRecallExecutor.recall(sceneContext);
            // 执行场景增强
            sceneEnhanceExecutor.enhance(sceneContext);
        } catch (TechRecommendException te) {
            log.error("scene recall custom error", te);
        } catch (Exception e) {
            log.error("scene recall occur error", e);
        }
    }

}

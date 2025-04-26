package com.tech.recommend.domain.api.service;

import com.tech.recommend.domain.api.context.SceneContext;

/**
 * 场景维度召回执行
 *
 * @author winding bubu
 * @since 2025/04/24
 */
public interface ISceneRecallService {

    /**
     * 场景召回
     *
     * @param sceneContext 场景上下文
     */
    void recall(SceneContext sceneContext);

}

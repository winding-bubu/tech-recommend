package com.tech.recommend.domain.biz.scene.service;

import com.tech.recommend.common.configuration.config.EnhanceConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationFactory;
import com.tech.recommend.domain.api.biz.IEnhanceService;
import com.tech.recommend.domain.api.context.EnhanceContext;
import com.tech.recommend.domain.api.context.SceneContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 场景增强服务
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Component
public class SceneEnhanceExecutor {

    @Resource
    private ConfigurationFactory configurationFactory;

    @Resource
    private IEnhanceService enhanceService;

    /**
     * 场景增强服务执行
     * 
     * @param sceneContext 上下文
     */
    public void enhance(SceneContext sceneContext) { 
        
        // 增强配置获取
        List<EnhanceConfig> sceneEnhanceConfigs = configurationFactory.getSceneEnhanceConfigs(sceneContext.getSceneId());
        if (CollectionUtils.isEmpty(sceneEnhanceConfigs)) {
            return;
        }

        // 上下文转换
        List<String> enhanceIds = sceneEnhanceConfigs.stream()
                .map(EnhanceConfig::getEnhanceId)
                .collect(Collectors.toList());
        EnhanceContext enhanceContext = new EnhanceContext();
        enhanceContext.setRequestId(sceneContext.getRequestId());
        enhanceContext.setSourceType("scene");
        enhanceContext.setSceneId(sceneContext.getSceneId());
        enhanceContext.setEnhanceIds(enhanceIds);
        enhanceContext.setResultItems(sceneContext.getResultItems());

        // 执行
        enhanceService.enhance(enhanceContext);
    }

}

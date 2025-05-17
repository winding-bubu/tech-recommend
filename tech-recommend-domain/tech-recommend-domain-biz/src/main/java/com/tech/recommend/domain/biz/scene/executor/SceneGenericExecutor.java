package com.tech.recommend.domain.biz.scene.executor;

import com.tech.recommend.common.configuration.config.GenericConfig;
import com.tech.recommend.common.configuration.factory.ConfigurationLoader;
import com.tech.recommend.domain.api.biz.IGenericService;
import com.tech.recommend.domain.api.context.GenericContext;
import com.tech.recommend.domain.api.context.SceneContext;
import com.tech.recommend.domain.api.model.generic.GenericResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 场景泛化服务
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Component
public class SceneGenericExecutor {

    @Resource
    private ConfigurationLoader configurationLoader;

    @Resource
    private IGenericService genericService;

    /**
     * 场景泛化执行
     * 
     * @param sceneContext 上下文
     */
    public void generic(SceneContext sceneContext) {

        // 泛化配置获取
        List<GenericConfig> sceneGenericConfigs = configurationLoader.getSceneGenericConfigs(sceneContext.getSceneId());
        if (CollectionUtils.isEmpty(sceneGenericConfigs)) {
            return;
        }

        // 上下文转换
        List<String> genericIds = sceneGenericConfigs.stream()
                .map(GenericConfig::getGenericId)
                .collect(Collectors.toList());
        GenericContext genericContext = new GenericContext();
        genericContext.setSourceType("scene");
        genericContext.setSceneId(sceneContext.getSceneId());
        genericContext.setGenericIds(genericIds);
        genericContext.setRequestId(sceneContext.getRequestId());
        genericContext.setParams(sceneContext.cloneParams());

        // 执行泛化
        GenericResponse genericResponse = genericService.generic(genericContext);
        if (Objects.isNull(genericResponse) || MapUtils.isEmpty(genericResponse.getData())) {
            return;
        }

        // 结果处理
        sceneContext.getParams().putAll(genericResponse.getData());
    }

}

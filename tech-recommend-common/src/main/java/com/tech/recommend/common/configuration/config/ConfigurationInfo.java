package com.tech.recommend.common.configuration.config;

import com.tech.recommend.common.thread.pool.DynamicThreadPool;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一配置集合
 *
 * @author winding bubu
 * @since 2025/05/03
 */
@Data
public class ConfigurationInfo {

    /**
     * 场景配置
     */
    private SceneConfig sceneConfig;

    /**
     * 场景泛化配置
     */
    private List<GenericConfig> sceneGenericConfigs = new ArrayList<>();

    /**
     * 场景增强配置
     */
    private List<EnhanceConfig> sceneEnhanceConfigs = new ArrayList<>();

    /**
     * 渠道配置
     */
    private List<ChannelConfig> channelConfigs = new ArrayList<>();

    /**
     * 渠道泛化配置 [渠道ID:泛化配置1集合]
     */
    private Map<String, List<GenericConfig>> channelGenericConfigs = new HashMap<>();

    /**
     * 渠道增强配置 [渠道ID:增强配置集合]
     */
    private Map<String, List<EnhanceConfig>> channelEnhanceConfigs = new HashMap<>();

    /**
     * 模板配置 [渠道ID:模板配置]
     */
    private Map<String, TemplateConfig> templateConfigs = new HashMap<>();

    /**
     * 场景维度的动态线程池
     */
    private DynamicThreadPool sceneThreadPool;

    /**
     * 索引配置 [模板ID:索引配置]
     */
    private Map<String, IndexConfig> indexConfigs = new HashMap<>();

    /**
     * dsl配置 [模板ID:dsl]
     */
    private Map<String, DslConfig> dslConfigs = new HashMap<>();

    public List<GenericConfig> getGenericConfigs() {
        List<GenericConfig> genericConfigs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sceneGenericConfigs)) {
            genericConfigs.addAll(sceneGenericConfigs);
        }
        channelGenericConfigs.forEach((key, value) -> {
            if (CollectionUtils.isNotEmpty(value)) {
                genericConfigs.addAll(value);
            }
        });
        return genericConfigs;
    }

}

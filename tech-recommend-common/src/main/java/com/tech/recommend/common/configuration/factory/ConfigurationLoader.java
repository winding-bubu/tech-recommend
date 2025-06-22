package com.tech.recommend.common.configuration.factory;

import com.tech.recommend.common.constant.ErrorCodeEnum;
import com.tech.recommend.common.constant.RelationEnum;
import com.tech.recommend.common.configuration.config.*;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.common.thread.pool.DynamicThreadPool;
import com.tech.recommend.common.thread.pool.DynamicThreadPoolFactory;
import com.tech.recommend.common.util.ConfigParserUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * 配置统一管理工厂
 *
 * @author winding bubu
 * @since 2025/05/03
 */
@Component
public class ConfigurationLoader {

    /**
     * bean注册时注入 [sceneId:场景配置]
     */
    private static final Map<String, ConfigurationInfo> configurationInfos = new HashMap<>();

    @Resource
    private ConfigParserUtil configParserUtil;
    
    @Resource
    private DynamicThreadPoolFactory dynamicThreadPoolFactory;

    @PostConstruct
    public void initConfig() {
        this.configRefresh();
    }

    /**
     * config刷新方法，1、容器启动时调用刷新 2、后续手动触发刷新
     */
    private void configRefresh() {
        List<SceneConfig> sceneConfigs = configParserUtil.getSceneConfigs();
        List<ChannelConfig> channelConfigs = configParserUtil.getChannelConfigs();
        Map<String, ChannelConfig> channelConfigMap = new HashMap<>();
        for (ChannelConfig channelConfig : channelConfigs) {
            channelConfigMap.put(channelConfig.getChannelId(), channelConfig);
        }
        List<TemplateConfig> templateConfigs = configParserUtil.getTemplateConfigs();
        Map<String, TemplateConfig> templateConfigMap = new HashMap<>();
        for (TemplateConfig templateConfig : templateConfigs) {
            templateConfigMap.put(templateConfig.getTemplateId(), templateConfig);
        }
        List<GenericConfig> genericConfigs = configParserUtil.getGenericConfigs();
        Map<String, GenericConfig> genericConfigMap = new HashMap<>();
        for (GenericConfig genericConfig : genericConfigs) {
            genericConfigMap.put(genericConfig.getGenericId(), genericConfig);
        }
        List<RelationConfig> relationConfigs = configParserUtil.getRelationConfigs();
        Map<String, Map<String, List<RelationConfig>>> relationConfigMap = new HashMap<>();
        for (RelationConfig relationConfig : relationConfigs) {
            relationConfigMap.putIfAbsent(relationConfig.getRelationType(), new HashMap<>());
            relationConfigMap.get(relationConfig.getRelationType()).putIfAbsent(relationConfig.getRelationFrom(), new ArrayList<>());
            relationConfigMap.get(relationConfig.getRelationType()).get(relationConfig.getRelationFrom()).add(relationConfig);
        }
        List<EnhanceConfig> enhanceConfigs = configParserUtil.getEnhanceConfigs();
        Map<String, EnhanceConfig> enhanceConfigMap = new HashMap<>();
        for (EnhanceConfig enhanceConfig : enhanceConfigs) {
            enhanceConfigMap.put(enhanceConfig.getEnhanceId(), enhanceConfig);
        }
        List<IndexConfig> indexConfigs = configParserUtil.getIndexConfigs();
        Map<String, IndexConfig> indexConfigMap = new HashMap<>();
        for (IndexConfig indexConfig : indexConfigs) {
            indexConfigMap.put(indexConfig.getIndexId(), indexConfig);
        }
        List<DslConfig> dslConfigs = configParserUtil.getDslConfigs();
        Map<String, DslConfig> dslConfigMap = new HashMap<>();
        for (DslConfig dslConfig : dslConfigs) {
            dslConfigMap.put(dslConfig.getId(), dslConfig);
        }
        for (SceneConfig sceneConfig : sceneConfigs) {
            ConfigurationInfo configurationInfo = new ConfigurationInfo();
            configurationInfo.setSceneConfig(sceneConfig);
            // 场景关联泛化
            List<RelationConfig> sceneGenericRelations = Optional.ofNullable(relationConfigMap.get(RelationEnum.SCENE_GENERIC.getCode()))
                    .map(sceneGenericRelation -> sceneGenericRelation.get(sceneConfig.getSceneId()))
                    .orElse(new ArrayList<>());
            for (RelationConfig relationConfig : sceneGenericRelations) {
                configurationInfo.getSceneGenericConfigs().add(genericConfigMap.get(relationConfig.getRelationTo()));
            }
            // 场景关联增强
            List<RelationConfig> sceneEnhanceRelations = Optional.ofNullable(relationConfigMap.get(RelationEnum.SCENE_ENHANCE.getCode()))
                    .map(sceneEnhanceRelation -> sceneEnhanceRelation.get(sceneConfig.getSceneId()))
                    .orElse(new ArrayList<>());
            for (RelationConfig relationConfig : sceneEnhanceRelations) {
                configurationInfo.getSceneEnhanceConfigs().add(enhanceConfigMap.get(relationConfig.getRelationTo()));
            }
            // 场景关联渠道
            List<RelationConfig> sceneChannelRelations = Optional.ofNullable(relationConfigMap.get(RelationEnum.SCENE_CHANNEL.getCode()))
                    .map(sceneChannelRelation -> sceneChannelRelation.get(sceneConfig.getSceneId()))
                    .orElse(new ArrayList<>());
            for (RelationConfig relationConfig : sceneChannelRelations) {
                configurationInfo.getChannelConfigs().add(channelConfigMap.get(relationConfig.getRelationTo()));
            }
            // 场景关联线程池
            String poolId = Optional.ofNullable(relationConfigMap.get(RelationEnum.SCENE_POOL.getCode()))
                    .map(relationMap -> relationMap.get(sceneConfig.getSceneId()))
                    .map(relations -> relations.get(0))
                    .map(RelationConfig::getRelationTo)
                    .orElse(null);
            configurationInfo.setSceneThreadPool(dynamicThreadPoolFactory.getPoolById(poolId));
            // 渠道处理
            for (ChannelConfig channelConfig : configurationInfo.getChannelConfigs()) {
                // 渠道关联泛化
                List<RelationConfig> channelGenericRelations = Optional.ofNullable(relationConfigMap.get(RelationEnum.CHANNEL_GENERIC.getCode()))
                        .map(channelGenericRelation -> channelGenericRelation.get(channelConfig.getChannelId()))
                        .orElse(new ArrayList<>());
                for (RelationConfig relationConfig : channelGenericRelations) {
                    configurationInfo.getChannelGenericConfigs().putIfAbsent(channelConfig.getChannelId(), new ArrayList<>());
                    configurationInfo.getChannelGenericConfigs().get(channelConfig.getChannelId()).add(genericConfigMap.get(relationConfig.getRelationTo()));
                }
                // 渠道关联增强
                List<RelationConfig> channelEnhanceRelations = Optional.ofNullable(relationConfigMap.get(RelationEnum.CHANNEL_ENHANCE.getCode()))
                        .map(channelEnhanceRelation -> channelEnhanceRelation.get(channelConfig.getChannelId()))
                        .orElse(new ArrayList<>());
                for (RelationConfig relationConfig : channelEnhanceRelations) {
                    configurationInfo.getChannelEnhanceConfigs().putIfAbsent(channelConfig.getChannelId(), new ArrayList<>());
                    configurationInfo.getChannelEnhanceConfigs().get(channelConfig.getChannelId()).add(enhanceConfigMap.get(relationConfig.getRelationTo()));
                }
                // 渠道关联模板
                List<RelationConfig> channelTemplateRelations = Optional.ofNullable(relationConfigMap.get(RelationEnum.CHANNEL_TEMPLATE.getCode()))
                        .map(channelTemplateRelation -> channelTemplateRelation.get(channelConfig.getChannelId()))
                        .orElse(new ArrayList<>());
                for (RelationConfig relationConfig : channelTemplateRelations) {
                    configurationInfo.getTemplateConfigs().put(channelConfig.getChannelId(), templateConfigMap.get(relationConfig.getRelationTo()));
                }
            }
            // 模板处理
            for (TemplateConfig templateConfig : templateConfigs) {
                // 模板关联索引
                List<RelationConfig> templateIndexRelations = Optional.ofNullable(relationConfigMap.get(RelationEnum.TEMPLATE_INDEX.getCode()))
                        .map(templateIndexRelation -> templateIndexRelation.get(templateConfig.getTemplateId()))
                        .orElse(new ArrayList<>());
                for (RelationConfig relationConfig : templateIndexRelations) {
                    configurationInfo.getIndexConfigs().put(templateConfig.getTemplateId(), indexConfigMap.get(relationConfig.getRelationTo()));
                }
                // 模板关联dsl
                List<RelationConfig> templateDslRelations = Optional.ofNullable(relationConfigMap.get(RelationEnum.TEMPLATE_DSL.getCode()))
                        .map(templateDslRelation -> templateDslRelation.get(templateConfig.getTemplateId()))
                        .orElse(new ArrayList<>());
                for (RelationConfig relationConfig : templateDslRelations) {
                    configurationInfo.getDslConfigs().put(templateConfig.getTemplateId(), dslConfigMap.get(relationConfig.getRelationTo()));
                }
            }
            configurationInfos.put(sceneConfig.getSceneId(), configurationInfo);
        }
    }

    /**
     * 获取场景配置
     * 
     * @param sceneId 场景ID
     * @return 场景配置
     */
    public SceneConfig getSceneConfig(String sceneId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getSceneConfig();
    }

    /**
     * 获取场景关联泛化配置
     * 
     * @param sceneId 场景ID
     * @return 场景关联的全部泛化配置
     */
    public List<GenericConfig> getSceneGenericConfigs(String sceneId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getSceneGenericConfigs();
    }

    /**
     * 获取场景关联增强配置
     * 
     * @param sceneId 场景ID
     * @return 场景关联的全部增强配置
     */
    public List<EnhanceConfig> getSceneEnhanceConfigs(String sceneId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getSceneEnhanceConfigs();
    }

    /**
     * 获取场景关联的动态线程池
     * 
     * @param sceneId 场景ID
     * @return 动态线程池
     */
    public DynamicThreadPool getSceneThreadPool(String sceneId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getSceneThreadPool();
    }

    /**
     * 获取全部渠道配置
     * 
     * @param sceneId 场景ID
     * @return 渠道配置集合
     */
    public List<ChannelConfig> getChannelConfigs(String sceneId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getChannelConfigs();
    }

    /**
     * 获取指定渠道配置
     * 
     * @param sceneId 场景ID
     * @param channelId 渠道ID
     * @return 渠道配置
     */
    public ChannelConfig getChannelConfig(String sceneId, String channelId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getChannelConfigs().stream()
                .filter(channelConfig -> channelConfig.getChannelId().equals(channelId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取渠道泛化配置集合
     * 
     * @param sceneId 场景ID
     * @param channelId 渠道ID
     * @return 渠道对应的泛化配置集合
     */
    public List<GenericConfig> getChannelGenericConfigs(String sceneId, String channelId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getChannelGenericConfigs().get(channelId);
    }

    /**
     * 获取渠道增强配置集合
     * 
     * @param sceneId 场景ID
     * @param channelId 渠道ID
     * @return 渠道对应的增强配置集合
     */
    public List<EnhanceConfig> getChannelEnhanceConfigs(String sceneId, String channelId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getChannelEnhanceConfigs().get(channelId);
    }

    /**
     * 获取渠道对应的模板配置
     * 
     * @param sceneId 场景ID
     * @param channelId 渠道ID
     * @return 模板配置
     */
    public TemplateConfig getChannelTemplateConfig(String sceneId, String channelId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getTemplateConfigs().get(channelId);
    }

    /**
     * 获取模板配置
     * 
     * @param sceneId 场景ID
     * @param templateId 模板ID
     * @return 模板配置
     */
    public TemplateConfig getTemplateConfig(String sceneId, String templateId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getTemplateConfigs().values().stream()
                .filter(templateConfig -> templateConfig.getTemplateId().equals(templateId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取泛化配置
     * 
     * @param sceneId 场景ID
     * @param genericId 泛化ID
     * @return 泛化配置
     */
    public GenericConfig getGenericConfig(String sceneId, String genericId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getGenericConfigs().stream()
                .filter(genericConfig -> genericConfig.getGenericId().equals(genericId))
                .findFirst()
                .orElse(null);
    }

    public EnhanceConfig getEnhanceConfig(String sceneId, String enhanceId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getEnhanceConfigs().stream()
                .filter(enhanceConfig -> enhanceConfig.getEnhanceId().equals(enhanceId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取模板对应的索引配置
     * 
     * @param sceneId 场景ID
     * @param templateId 模板ID
     * @return 索引配置
     */
    public IndexConfig getTemplateIndexConfig(String sceneId, String templateId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getIndexConfigs().get(templateId);
    }

    /**
     * 获取模板对应的dsl配置
     * 
     * @param sceneId 场景ID
     * @param templateId 模板ID
     * @return dsl配置
     */
    public DslConfig getTemplateDslConfig(String sceneId, String templateId) {
        this.configCheck(sceneId);
        return configurationInfos.get(sceneId).getDslConfigs().get(templateId);
    }

    private void configCheck(String sceneId) {
        if (configurationInfos.get(sceneId) == null) {
            throw new TechRecommendException(ErrorCodeEnum.CONFIG_NOT_EXIST);
        }
    }

}

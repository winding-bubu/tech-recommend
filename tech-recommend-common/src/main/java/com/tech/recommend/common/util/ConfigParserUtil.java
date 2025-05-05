package com.tech.recommend.common.util;

import com.alibaba.fastjson2.JSON;
import com.tech.recommend.common.configuration.config.*;
import com.tech.recommend.common.thread.config.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件解析工具类
 *
 * @author winding bubu
 * @since 2025/04/28
 */
@Slf4j
@Component
public class ConfigParserUtil {

    private static final String SCENE_JSON_PATTERN = "classpath:scene/*/scene.json";

    private static final String CHANNEL_JSON_PATTERN = "classpath:scene/*/channel.json";

    private static final String GENERIC_JSON_PATTERN = "classpath:scene/*/generic.json";

    private static final String TEMPLATE_JSON_PATTERN = "classpath:scene/*/template.json";

    private static final String RELATION_JSON_PATTERN = "classpath:scene/*/relation.json";

    private static final String ENHANCE_JSON_PATTERN = "classpath:scene/*/enhance.json";

    private static final String DSL_JSON_PATTERN = "classpath:scene/*/dsl.json";

    /**
     * 解析获取线程池配置
     *
     * @return 线程池配置集合
     */
    public List<ThreadPoolConfig> getThreadPoolConfigs() {
        return this.parseJsonFileArray("pool/dynamicPoolConfig.json", ThreadPoolConfig.class);
    }

    /**
     * 获取全部场景配置
     * 
     * @return 场景配置集合
     */
    public List<SceneConfig> getSceneConfigs() {
        List<SceneConfig> result = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources(SCENE_JSON_PATTERN);

            for (Resource resource : resources) {
                // 获取相对路径
                String path = this.extractResourcePath(resource);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                // 文件解析
                SceneConfig sceneConfig = parseJsonFile(path, SceneConfig.class);
                result.add(sceneConfig);
            }
        } catch (Exception e) {
            log.error("Failed to load scene configurations", e);
        }

        return result;
    }

    /**
     * 获取全部渠道配置
     * 
     * @return 渠道配置集合
     */
    public List<ChannelConfig> getChannelConfigs() {
        List<ChannelConfig> result = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources(CHANNEL_JSON_PATTERN);

            for (Resource resource : resources) {
                // 获取相对路径
                String path = this.extractResourcePath(resource);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                // 文件解析
                List<ChannelConfig> channelConfigs = parseJsonFileArray(path, ChannelConfig.class);
                result.addAll(channelConfigs);
            }
        } catch (Exception e) {
            log.error("Failed to load channel configurations", e);
        }

        return result;
    }

    /**
     * 获取全部泛化配置
     * 
     * @return 泛化配置集合
     */
    public List<GenericConfig> getGenericConfigs() {
        List<GenericConfig> result = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources(GENERIC_JSON_PATTERN);

            for (Resource resource : resources) {
                // 获取相对路径
                String path = this.extractResourcePath(resource);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                // 文件解析
                List<GenericConfig> genericConfigs = parseJsonFileArray(path, GenericConfig.class);
                result.addAll(genericConfigs);
            }
        } catch (Exception e) {
            log.error("Failed to load generic configurations", e);
        }

        return result;
    }

    /**
     * 获取全部模板配置
     * 
     * @return 模板配置集合
     */
    public List<TemplateConfig> getTemplateConfigs() {
        List<TemplateConfig> result = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources(TEMPLATE_JSON_PATTERN);

            for (Resource resource : resources) {
                // 获取相对路径
                String path = this.extractResourcePath(resource);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                // 文件解析
                List<TemplateConfig> templateConfigs = parseJsonFileArray(path, TemplateConfig.class);
                result.addAll(templateConfigs);
            }
        } catch (Exception e) {
            log.error("Failed to load template configurations", e);
        }

        return result;
    }

    /**
     * 获取全部关联配置
     * 
     * @return 关联配置集合
     */
    public List<RelationConfig> getRelationConfigs() {
        List<RelationConfig> result = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources(RELATION_JSON_PATTERN);

            for (Resource resource : resources) {
                // 获取相对路径
                String path = this.extractResourcePath(resource);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                // 文件解析
                List<RelationConfig> relationConfigs = parseJsonFileArray(path, RelationConfig.class);
                result.addAll(relationConfigs);
            }
        } catch (Exception e) {
            log.error("Failed to load relation configurations", e);
        }

        return result;
    }

    /**
     * 获取全部增强节点配置
     * 
     * @return 增强节点配置集合
     */
    public List<EnhanceConfig> getEnhanceConfigs() {
        List<EnhanceConfig> result = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources(ENHANCE_JSON_PATTERN);

            for (Resource resource : resources) {
                // 获取相对路径
                String path = this.extractResourcePath(resource);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                // 文件解析
                List<EnhanceConfig> enhanceConfigs = parseJsonFileArray(path, EnhanceConfig.class);
                result.addAll(enhanceConfigs);
            }
        } catch (Exception e) {
            log.error("Failed to load enhance configurations", e);
        }

        return result;
    }

    /**
     * 获取全部DSL配置
     *
     * @return DSL配置集合
     */
    public List<DslConfig> getDslConfigs() {
        List<DslConfig> result = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources(ENHANCE_JSON_PATTERN);

            for (Resource resource : resources) {
                // 获取相对路径
                String path = this.extractResourcePath(resource);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                // 文件解析
                List<DslConfig> dslConfigs = parseJsonFileArray(path, DslConfig.class);
                result.addAll(dslConfigs);
            }
        } catch (Exception e) {
            log.error("Failed to load enhance configurations", e);
        }

        return result;
    }

    /**
     * 资源路径提取
     * 
     * @param resource 资源
     * @return 路径
     */
    private String extractResourcePath(Resource resource) {
        try {
            if (Objects.isNull(resource)) {
                return null;
            }

            URI uri = resource.getURI();
            String uriStr = uri.toString();

            if (uriStr.contains("/classes/")) {
                int index = uriStr.indexOf("/classes/") + "/classes/".length();
                return uriStr.substring(index);
            }
        } catch (Exception e) {
            log.error("ConfigParserUtil extractResourcePath error", e);
        }
        return null;
    }

    /**
     * 获取索引配置
     * 
     * @return 索引配置集合
     */
    public List<IndexConfig> getIndexConfigs() {
        return this.parseJsonFileArray("index/index.json", IndexConfig.class);
    }

    /**
     * json 文件解析
     *
     * @param path 文件路径
     * @param clazz 对应的配置类
     * @return 配置类
     * @param <T> 泛型
     */
    private <T> T parseJsonFile(String path, Class<T> clazz) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            String configJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            if (StringUtils.isBlank(configJson)) {
                return null;
            }
            return JSON.parseObject(configJson, clazz);
        } catch (Exception e) {
            log.error("ConfigParserUtil parseJsonFile error", e);
            return null;
        }
    }

    /**
     * json 文件解析
     * 
     * @param path 文件路径
     * @param clazz 对应的配置类
     * @return 配置类
     * @param <T> 泛型
     */
    private <T> List<T> parseJsonFileArray(String path, Class<T> clazz) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            String configJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            if (StringUtils.isBlank(configJson)) {
                return new ArrayList<>();
            }
            return JSON.parseArray(configJson, clazz);
        } catch (Exception e) {
            log.error("ConfigParserUtil parseJsonFile error", e);
            return new ArrayList<>();
        }
    }

}

package com.tech.recommend.common.util;

import com.alibaba.fastjson2.JSON;
import com.tech.recommend.common.scene.config.ChannelConfig;
import com.tech.recommend.common.scene.config.GenericConfig;
import com.tech.recommend.common.scene.config.RelationConfig;
import com.tech.recommend.common.scene.config.SceneConfig;
import com.tech.recommend.web.TechRecommendApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 配置提取工具类测试
 *
 * @author winding bubu
 * @since 2025/05/03
 */
@Slf4j
@SpringBootTest(classes = TechRecommendApplication.class)
public class ConfigParserUtilTest {

    @Resource
    private ConfigParserUtil configParserUtil;

    @Test
    public void testGetSceneConfigs() {
        List<SceneConfig> sceneConfigs = configParserUtil.getSceneConfigs();
        log.info("场景配置: {}", JSON.toJSONString(sceneConfigs));
    }

    @Test
    public void testGetChannelConfigs() {
        List<ChannelConfig> channelConfigs = configParserUtil.getChannelConfigs();
        log.info("渠道配置: {}", JSON.toJSONString(channelConfigs));
    }

    @Test
    public void testGetGenericConfigs() {
        List<GenericConfig> genericConfigs = configParserUtil.getGenericConfigs();
        log.info("泛化配置: {}", JSON.toJSONString(genericConfigs));
    }

    @Test
    public void testGetRelationConfigs() {
        List<RelationConfig> relationConfigs = configParserUtil.getRelationConfigs();
        log.info("关联配置: {}", JSON.toJSONString(relationConfigs));
    }

}

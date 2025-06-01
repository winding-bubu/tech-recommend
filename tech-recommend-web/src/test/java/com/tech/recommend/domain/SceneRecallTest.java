package com.tech.recommend.domain;

import com.tech.recommend.domain.api.biz.ISceneRecallService;
import com.tech.recommend.domain.api.context.SceneContext;
import com.tech.recommend.web.TechRecommendApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.UUID;

/**
 * 场景召回测试
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Slf4j
@SpringBootTest(classes = TechRecommendApplication.class)
public class SceneRecallTest {

    @Resource
    private ISceneRecallService sceneRecallService;

    @Test
    public void testExampleSceneRecall() {

        // 构建上下文
        SceneContext sceneContext = new SceneContext();
        sceneContext.setRequestId(UUID.randomUUID().toString());
        sceneContext.setSceneId("homeRecommendation");
        sceneContext.setParams(new HashMap<>());

        // 执行召回
        sceneRecallService.recall(sceneContext);

    }

}

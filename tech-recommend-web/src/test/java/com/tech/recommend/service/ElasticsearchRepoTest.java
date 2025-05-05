package com.tech.recommend.service;

import com.alibaba.fastjson2.JSON;
import com.tech.recommend.facade.model.ResultItem;
import com.tech.recommend.service.repo.es.ElasticsearchRepo;
import com.tech.recommend.web.TechRecommendApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * es查询单测
 *
 * @author winding bubu
 * @since 2025/05/05
 */
@Slf4j
@SpringBootTest(classes = TechRecommendApplication.class)
public class ElasticsearchRepoTest {

    @Resource
    private ElasticsearchRepo elasticsearchRepo;

    @Test
    public void testSearch() {

        String dsl = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"id\": \"park_rocky-mountain\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        List<ResultItem> search = elasticsearchRepo.search("my-index", dsl);
        log.info(JSON.toJSONString(search));
    }

}

package com.tech.recommend.service.repo.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson2.JSONObject;
import com.tech.recommend.common.exception.TechRecommendException;
import com.tech.recommend.facade.model.ResultItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

import static com.tech.recommend.common.constant.ErrorCodeEnum.ES_ERROR;

/**
 * es查询防腐层
 *
 * @author winding bubu
 * @since 2025/05/05
 */
@Slf4j
@Component
public class ElasticsearchRepo {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    /**
     * es查询
     * 
     * @param index 索引
     * @param queryDsl 查询dsl
     * @return 查询结果
     */
    public List<ResultItem> search(String index, String queryDsl) {
        try {
            // 构建请求
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index(index)
                    .withJson(new StringReader(queryDsl))
            );
            // 执行查询
            SearchResponse<JSONObject> response = elasticsearchClient.search(searchRequest, JSONObject.class);
            // 结果解析
            return response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .map(this::convertToResultItem)
                    .collect(Collectors.toList());
        }  catch (IOException e) {
            log.error("ES查询IO异常 | Query: {} | Error: {}", queryDsl, e.getMessage(), e);
            throw new TechRecommendException(ES_ERROR);
        } catch (Exception e) {
            log.error("ES查询未知异常 | Query: {} | Error: {}", queryDsl, e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private ResultItem convertToResultItem(JSONObject esJsonResult) {

        return ResultItem.builder()
                .itemId(String.valueOf(esJsonResult.get("id")))
                .recallTags(new HashSet<>())
                .extra(new HashMap<>())
                .build();
    }

}

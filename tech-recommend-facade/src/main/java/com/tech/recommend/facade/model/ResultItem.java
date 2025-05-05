package com.tech.recommend.facade.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

/**
 * 返回结果
 *
 * @author winding bubu
 * @since 2025/05/04
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultItem {

    /**
     * 结果ID
     */
    private String itemId;

    /**
     * 召回标识
     */
    private Set<String> recallTags;

    /**
     * 扩展参数
     */
    private Map<String, Object> extra;

}

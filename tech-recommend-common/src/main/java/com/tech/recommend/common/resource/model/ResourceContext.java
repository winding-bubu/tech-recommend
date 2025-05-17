package com.tech.recommend.common.resource.model;

import com.tech.recommend.common.util.TypeUtil;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 资源加载上下文
 *
 * @author winding bubu
 * @since 2025/05/15
 */
@Data
public class ResourceContext {

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 资源ID
     */
    private String resourceId;

    /**
     * 查询参数
     */
    private Map<String, Object> params;


    public <T> T getParam(String key) {
        return (T)params.get(key);
    }

    public void putParam(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }

}

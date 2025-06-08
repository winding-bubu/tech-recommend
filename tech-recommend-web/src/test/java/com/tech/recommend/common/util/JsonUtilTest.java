package com.tech.recommend.common.util;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * json相关方法验证
 *
 * @author winding bubu
 * @since 2025/06/08
 */

@Slf4j
public class JsonUtilTest {

    @Test
    public void testJson() {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("key", "value");
        System.out.println(jsonObject.toJSONString());
        jsonObject.put("key", "value2");
        System.out.println(jsonObject.toJSONString());
    }

}

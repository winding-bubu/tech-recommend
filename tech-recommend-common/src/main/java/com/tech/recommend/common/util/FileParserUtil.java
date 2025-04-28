package com.tech.recommend.common.util;

import com.alibaba.fastjson2.JSON;
import com.tech.recommend.common.thread.config.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件解析工具类
 *
 * @author winding bubu
 * @since 2025/04/28
 */
@Slf4j
@Component
public class FileParserUtil {

    /**
     * 解析获取线程池配置
     *
     * @return 线程池配置集合
     */
    public List<ThreadPoolConfig> getThreadPoolConfigs() {
        return this.parseJsonFile("pool/dynamicPoolConfig.json", ThreadPoolConfig.class);
    }

    private <T> List<T> parseJsonFile(String path, Class<T> clazz) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            String configJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            if (StringUtils.isBlank(configJson)) {
                return new ArrayList<>();
            }
            return JSON.parseArray(configJson, clazz);
        } catch (Exception e) {
            log.error("FileParserUtil parseJsonFile error", e);
            return new ArrayList<>();
        }
    }

}

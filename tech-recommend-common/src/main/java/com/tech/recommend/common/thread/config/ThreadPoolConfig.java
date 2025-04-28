package com.tech.recommend.common.thread.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 线程池配置
 *
 * @author winding bubu
 * @since 2025/04/28
 */
@Data
public class ThreadPoolConfig {

    /**
     * 线程池ID
     */
    private String poolId;

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize;

    /**
     * 队列数
     */
    private Integer workQueueSize;

    /**
     * 拒绝策略
     */
    private Integer rejectPolicy;

    /**
     * 描述
     */
    private String description;

    public boolean isValid() {
        return StringUtils.isNotBlank(poolId) && Objects.nonNull(corePoolSize) && Objects.nonNull(maxPoolSize) && Objects.nonNull(workQueueSize) && Objects.nonNull(rejectPolicy);
    }

}

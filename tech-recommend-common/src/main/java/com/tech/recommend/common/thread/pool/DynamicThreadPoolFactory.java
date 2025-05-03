package com.tech.recommend.common.thread.pool;

import com.tech.recommend.common.constant.RejectPolicyEnum;
import com.tech.recommend.common.thread.config.ThreadPoolConfig;
import com.tech.recommend.common.util.ConfigParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态线程池工厂
 *
 * @author winding bubu
 * @since 2025/04/28
 */
@Slf4j
@Component
public class DynamicThreadPoolFactory {

    @Resource
    private ConfigParserUtil configParserUtil;

    private static final String POOL_PREFIX = "techRecommend_";

    /**
     * 动态线程池集合
     */
    private final Map<String, DynamicThreadPool> dynamicThreadPoolMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void initDynamicThreadPool() {
        List<ThreadPoolConfig> threadPoolConfigs = configParserUtil.getThreadPoolConfigs();
        if (CollectionUtils.isEmpty(threadPoolConfigs)) {
            return;
        }
        threadPoolConfigs.forEach(threadPoolConfig -> {
            DynamicThreadPool dynamicThreadPool = this.createPool(threadPoolConfig);
            if (Objects.nonNull(dynamicThreadPool)) {
                dynamicThreadPoolMap.put(threadPoolConfig.getPoolId(), dynamicThreadPool);
            }
        });
    }

    /**
     * 动态线程池获取
     *
     * @param poolId 线程池ID
     * @return 动态线程池
     */
    public DynamicThreadPool getPoolById(String poolId) {
        return dynamicThreadPoolMap.containsKey(poolId) ? dynamicThreadPoolMap.get(poolId) : dynamicThreadPoolMap.get("default");
    }

    /**
     * 动态线程池创建
     *
     * @param threadPoolConfig 线程池配置
     * @return 动态线程池
     */
    private DynamicThreadPool createPool(ThreadPoolConfig threadPoolConfig) {
        if (Objects.isNull(threadPoolConfig) || !threadPoolConfig.isValid()) {
            return null;
        }
        String poolName = POOL_PREFIX + threadPoolConfig.getPoolId();
        return new DynamicThreadPool(poolName, threadPoolConfig.getCorePoolSize(), threadPoolConfig.getMaxPoolSize(),
            threadPoolConfig.getWorkQueueSize(), RejectPolicyEnum.getPolicyByCode(threadPoolConfig.getRejectPolicy()));
    }

}

package com.tech.recommend.common.thread.pool;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 动态线程池
 *
 * @author winding bubu
 * @since 2025/04/27
 */
@Slf4j
@Getter
public class DynamicThreadPool {

    /**
     * 线程池名称
     */
    private final String name;

    /**
     * 队列
     */
    private final BlockingQueue<Runnable> workQueue;

    /**
     * 线程池
     */
    private final ThreadPoolExecutor executor;


    public DynamicThreadPool(String name, Integer corePoolSize, Integer maximumPoolSize, Integer workQueueSize, RejectedExecutionHandler rejectedHandler) {
        if (corePoolSize > 300) {
            log.warn("corePoolSize > 300");
            corePoolSize = 300;
        }
        if (maximumPoolSize > 1000) {
            log.warn("maximumPoolSize > 1000");
            maximumPoolSize = 1000;
        }
        this.name = name;
        this.workQueue = new ArrayBlockingQueue<>(workQueueSize);
        this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, workQueue, rejectedHandler);
        this.executor.prestartAllCoreThreads();
        this.executor.allowCoreThreadTimeOut(false);
    }

}

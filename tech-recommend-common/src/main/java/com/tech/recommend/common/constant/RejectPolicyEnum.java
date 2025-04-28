package com.tech.recommend.common.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池拒绝异常枚举
 *
 * @author winding bubu
 * @since 2025/04/28
 */
@Getter
public enum RejectPolicyEnum {

    ABORT(0, ThreadPoolExecutor.AbortPolicy.class),

    CALLER_RUNS(1, ThreadPoolExecutor.CallerRunsPolicy.class),

    DISCARD(2, ThreadPoolExecutor.DiscardPolicy.class),

    DISCARD_OLD(3, ThreadPoolExecutor.DiscardOldestPolicy.class),
    ;

    private final int code;

    private final Class<? extends RejectedExecutionHandler> policyClass;

    RejectPolicyEnum(int code, Class<? extends RejectedExecutionHandler> policyClass) {
        this.code = code;
        this.policyClass = policyClass;
    }

    // 静态 Map 缓存 code 和实例的映射
    private static final Map<Integer, RejectedExecutionHandler> CODE_TO_POLICY = new HashMap<>();

    static {
        for (RejectPolicyEnum policy : values()) {
            try {
                RejectedExecutionHandler instance = policy.policyClass.getDeclaredConstructor().newInstance();
                CODE_TO_POLICY.put(policy.code, instance);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize policy instances", e);
            }
        }
    }

    public static RejectedExecutionHandler getPolicyByCode(int code) {
        return CODE_TO_POLICY.get(code);
    }

}

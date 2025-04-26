package com.tech.recommend.domain.api.context;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景上下文
 *
 * @author winding bubu
 * @since 2025/04/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SceneContext extends BaseContext {

    /**
     * 场景ID
     */
    private String sceneId;

}

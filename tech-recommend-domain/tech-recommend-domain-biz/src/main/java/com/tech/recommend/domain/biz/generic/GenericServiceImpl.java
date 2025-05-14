package com.tech.recommend.domain.biz.generic;

import com.tech.recommend.domain.api.context.GenericContext;
import com.tech.recommend.domain.api.model.generic.GenericResponse;
import com.tech.recommend.domain.api.biz.IGenericService;
import com.tech.recommend.domain.api.service.IGenericProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 泛化服务实现
 *
 * @author winding bubu
 * @since 2025/04/26
 */
@Component
public class GenericServiceImpl implements IGenericService {

    /**
     * 泛化bean实例集合
     */
    private static final Map<String, IGenericProcessor> genericProcessorMap = new HashMap<>();

    public GenericServiceImpl(List<IGenericProcessor> genericProcessors) {
        genericProcessors.forEach(processor -> genericProcessorMap.put(processor.genericId(), processor));
    }

    @Override
    public GenericResponse generic(GenericContext genericContext) {
        if (CollectionUtils.isEmpty(genericContext.getGenericIds())) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        for (String genericId : genericContext.getGenericIds()) {
            if (!genericProcessorMap.containsKey(genericId)) {
                continue;
            }
            Map<String, Object> resMap = genericProcessorMap.get(genericId).doGeneric(genericContext);
            if (MapUtils.isNotEmpty(resMap)) {
                result.putAll(resMap);
            }
        }
        if (MapUtils.isEmpty(result)) {
            return null;
        }
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setData(result);
        return genericResponse;
    }

}

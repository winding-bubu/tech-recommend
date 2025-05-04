package com.tech.recommend.domain.biz.generic;

import com.tech.recommend.domain.api.context.GenericContext;
import com.tech.recommend.domain.api.model.generic.GenericResponse;
import com.tech.recommend.domain.api.biz.IGenericService;
import com.tech.recommend.domain.api.service.IGenericProcessor;
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
    
    private static Map<String, IGenericProcessor> genericProcessorMap = new HashMap<>();

    public GenericServiceImpl(List<IGenericProcessor> genericProcessors) {
        genericProcessors.forEach(processor -> genericProcessorMap.put(processor.genericId(), processor));
    }

    @Override
    public GenericResponse generic(GenericContext genericContext) {
        return null;
    }

}

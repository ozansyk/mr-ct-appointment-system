package com.ozansoyak.mr_ct_appointment_system.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CommonService {

    private ModelMapper mapper;

    public <S, D> D map(S sourceInstance, Class<D> destinationTypeClass) {
        if (!ObjectUtils.isEmpty(mapper) && !ObjectUtils.isEmpty(mapper.getConfiguration())) {
            mapper.getConfiguration().setAmbiguityIgnored(true);
        }
        return mapper.map(sourceInstance, destinationTypeClass);
    }

    public <S, D> D map(S sourceInstance, Type destinationTypeClass) {
        if (!ObjectUtils.isEmpty(mapper) && !ObjectUtils.isEmpty(mapper.getConfiguration())) {
            mapper.getConfiguration().setAmbiguityIgnored(true);
        }
        return mapper.map(sourceInstance, destinationTypeClass);
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        if (!ObjectUtils.isEmpty(mapper) && !ObjectUtils.isEmpty(mapper.getConfiguration())) {
            mapper.getConfiguration().setAmbiguityIgnored(true);
        }
        return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
    }
}

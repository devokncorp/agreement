package com.example.demo.core.base;

import com.example.demo.core.api.IPostProcessor;
import com.example.demo.core.api.OvmExecutionTime;
import com.example.demo.core.entity.IOvmEntity;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class BasePostProcessor<E extends IOvmEntity> implements IPostProcessor<E> {

    public abstract E postProcess(E e);

    @OvmExecutionTime
    @Override
    public Collection<E> postProcessAgreement(Collection<E> t) {
        return t.parallelStream().map(e -> postProcess(e)).collect(Collectors.toList());
    }
}

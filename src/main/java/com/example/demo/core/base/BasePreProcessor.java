package com.example.demo.core.base;

import com.example.demo.core.api.IPreProcessor;
import com.example.demo.core.api.OvmExecutionTime;
import com.example.demo.core.entity.IOvmEntity;

import java.util.List;
import java.util.stream.Collectors;


public abstract class BasePreProcessor<E extends IOvmEntity> implements IPreProcessor<E> {

    public E processAllianzEntity(E p1){return p1;}

    public E processSbmEntity(E p1){return p1;}

    @OvmExecutionTime
    @Override
    public List<E> preProcessAllianz(List<E> t) {
        return t.parallelStream().map(e -> processAllianzEntity(e)).collect(Collectors.toList());
    }

    @OvmExecutionTime
    @Override
    public List<E> preProcessSbm(List<E> t) {
        return t.parallelStream().map(e -> processSbmEntity(e)).collect(Collectors.toList());
    }
}

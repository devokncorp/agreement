package com.example.demo.core.api;

import com.example.demo.core.entity.IOvmEntity;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface IPreProcessor<E extends IOvmEntity>{
    default void onPreProcessEnd(Collection<E> t){}

    default List<E> preProcessAllianz(List<E> t){return t;};

    default List<E> preProcessSbm(List<E> t){return t;};
}
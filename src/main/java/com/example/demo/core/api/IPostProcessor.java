package com.example.demo.core.api;

import com.example.demo.core.entity.IOvmEntity;

import java.util.Collection;
import java.util.List;

public interface IPostProcessor<E extends IOvmEntity> {
    default void onPostProcessEnds(Collection<E> t){}
    default Collection<E> postProcessAgreement(Collection<E> t){return t;};
}
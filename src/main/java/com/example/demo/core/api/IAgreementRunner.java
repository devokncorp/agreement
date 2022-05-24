package com.example.demo.core.api;

import com.example.demo.core.entity.IOvmEntity;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface IAgreementRunner<HASH,R extends IOvmRequest,E extends IOvmEntity> {
     Collection<E> run(R req,
                       ICollector<R,List<E>> collector,
                       IPreProcessor<E> preProcessor,
                       IPostProcessor<E> postProcessor,
                       Function<E,HASH> hashFunction);
}

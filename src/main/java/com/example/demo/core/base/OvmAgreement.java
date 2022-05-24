package com.example.demo.core.base;

import com.example.demo.core.api.*;
import com.example.demo.core.*;
import com.example.demo.core.entity.IOvmEntity;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @param <E> the type of elements in this list
 * @param <R> the type of agreement request
 * @author Mustafa Okan
 * @see com.example.demo.core.AgreementRunner
 */
public abstract class OvmAgreement<H, R extends IOvmRequest, E extends IOvmEntity> extends AgreementRunner<H, R, E> {
    private ICollector<R, List<E>> collector;
    private IPreProcessor<E> preProcessor;
    private IPostProcessor<E> postProcessor;
    private Function<E, H> hashFunction;

    @OvmExecutionTime
    public Collection<E> run(R req) {
        return super.run(req, collector, preProcessor, postProcessor, hashFunction);
    }

    public OvmAgreement(ICollector<R, List<E>> collector, IPreProcessor<E> preProcessor, IPostProcessor<E> postProcessor, Function<E, H> hashFunction) {
        this.collector = collector;
        this.preProcessor = preProcessor;
        this.postProcessor = postProcessor;
        this.hashFunction = hashFunction;
    }

    public OvmAgreement(ICollector<R, List<E>> collector, IPreProcessor<E> preProcessor, IPostProcessor<E> postProcessor) {
        this.collector = collector;
        this.preProcessor = preProcessor;
        this.postProcessor = postProcessor;
    }

    public OvmAgreement(ICollector<R, List<E>> collector, IPreProcessor<E> preProcessor) {
        this.collector = collector;
        this.preProcessor = preProcessor;
    }

    public OvmAgreement(ICollector<R, List<E>> collector) {
        this.collector = collector;
    }

}

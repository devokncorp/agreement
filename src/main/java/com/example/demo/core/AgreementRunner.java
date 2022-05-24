package com.example.demo.core;

import com.example.demo.core.api.IOvmRequest;
import com.example.demo.core.api.IAgreementRunner;
import com.example.demo.core.api.ICollector;
import com.example.demo.core.api.IPostProcessor;
import com.example.demo.core.api.IPreProcessor;
import com.example.demo.core.entity.IOvmEntity;
import com.example.demo.core.exception.OvmDataAccessExcption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/***
 * @author Mustafa Okan
 */
@Slf4j
public abstract class AgreementRunner<HASH, R extends IOvmRequest, E extends IOvmEntity> implements IAgreementRunner<HASH, R, E> {

    @SuppressWarnings(value = "chill")
    public Collection<E> run(R req, ICollector<R, List<E>> collector, IPreProcessor<E> preProcessor, IPostProcessor<E> postProcessor, Function<E, HASH> hashFunction) {
        Map<HASH, E> entityHashMapA = getEntityHashMap(getAllianzData(req, collector, preProcessor), hashFunction);
        Set<HASH> setA = entityHashMapA.keySet();

        Map<HASH, E> entityHashMapB = getEntityHashMap(getSbmData(req, collector, preProcessor), hashFunction);
        Set<HASH> setB = entityHashMapB.keySet();

        relativeComplements(setA, setB);

        Collection<E> agreement = entityHashMapA.values();
        notifyPreProcessEnds(preProcessor, agreement);

        return postProcess(postProcessor,entityHashMapA.values());
    }

    private <D> Map<D, E> getEntityHashMap(List<E> entityList, Function<E, D> hashFunction) {
        if(!CollectionUtils.isEmpty(entityList)){
            Collector<E, ?, ConcurrentHashMap<D, E>> concurrentHashMapCollector = Collectors.toConcurrentMap(
                    hashFunction,
                    Function.identity(),
                    (u, v) -> u,
                    () -> new ConcurrentHashMap<D, E>(entityList.size()));

            return entityList.parallelStream().collect(concurrentHashMapCollector);
        }
        return Collections.emptyMap();
    }

    private void relativeComplements(Set<HASH> setA, Set<HASH> setB) {
        Set<HASH> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        setA.removeAll(intersection);
        setB.removeAll(intersection);
    }

    private Collection<E> postProcess(IPostProcessor<E> postProcessor, Collection<E> agreementList) {
        if (Objects.nonNull(postProcessor)) {
            Collection<E> processedList = postProcessor.postProcessAgreement(agreementList);
            postProcessor.onPostProcessEnds(processedList);
            return processedList;
        }
        return agreementList;
    }

    private void notifyPreProcessEnds(IPreProcessor<E> preProcessor, Collection<E> agreement) {
        if (Objects.nonNull(preProcessor)) {
            preProcessor.onPreProcessEnd(agreement);
        }
    }

    private List<E> getAllianzData(R req, ICollector<R, List<E>> collector, IPreProcessor<E> preProcessor) throws OvmDataAccessExcption {
        List<E> list = collector.getAllinazEntityList(req);
        if (Objects.nonNull(preProcessor) && !CollectionUtils.isEmpty(list)) {
            return preProcessor.preProcessAllianz(list);
        }
        return list;
    }

    private List<E> getSbmData(R req, ICollector<R, List<E>> collector, IPreProcessor<E> preProcessor) throws OvmDataAccessExcption {
        List<E> list = collector.getSbmEntityList(req);
        if (Objects.nonNull(preProcessor) && !CollectionUtils.isEmpty(list)) {
            return preProcessor.preProcessSbm(list);
        }
        return list;
    }
}
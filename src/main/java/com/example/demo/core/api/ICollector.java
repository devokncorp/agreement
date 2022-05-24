package com.example.demo.core.api;

import com.example.demo.core.exception.OvmDataAccessExcption;


public interface ICollector<T extends IOvmRequest, R> {
//    @OvmExecutionTime
    @OvmExecutionTime
    R getAllinazEntityList(T t) throws OvmDataAccessExcption;
    @OvmExecutionTime
//    @OvmExecutionTime
    R getSbmEntityList(T t) throws OvmDataAccessExcption;
}
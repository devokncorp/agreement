package com.example.demo.core.process.collector;

import com.example.demo.controller.request.AggrementReuqest;
import com.example.demo.core.api.ICollector;
import com.example.demo.core.api.OvmExecutionTime;
import com.example.demo.core.entity.Payment;
import com.example.demo.core.exception.OvmDataAccessExcption;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentDataCollector implements ICollector<AggrementReuqest, List<Payment>> {

    @OvmExecutionTime
    @SneakyThrows(value = OvmDataAccessExcption.class)
    @Override
    public List<Payment> getAllinazEntityList(AggrementReuqest req)  {
        List<Payment> sourceDbResults = new ArrayList<>();//Arrays.asList(1, 2, 3, 4, 5, 8);
        int MAX = 5_000_000;
        for (int i = 0; i < MAX; i++) {
            sourceDbResults.add(Payment.builder().fileNo("lkkaaw" + i).order(Double.valueOf(Math.ceil(Math.random() * 100000)).intValue() + i).build());
        }
        sourceDbResults.remove(sourceDbResults.size() - 1);
        sourceDbResults.add(Payment.builder().fileNo("aa").order(1).build());
        return sourceDbResults;
    }

    @OvmExecutionTime
    @SneakyThrows(value = OvmDataAccessExcption.class)
    @Override
    public List<Payment> getSbmEntityList(AggrementReuqest aggrementReuqest)  {
        return null;
    }
}

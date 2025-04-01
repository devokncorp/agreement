package com.example.demo;

import com.example.demo.core.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AService {
    //    static
    private void f(List<Payment> DATA_A, List<Payment> DATA_B) {
        int MAX = 5_000_000;
        for (int i = 0; i < MAX; i++) {
            DATA_A.add(Payment.builder().fileNo("lkkaaw" + i).order(i).build());
            DATA_B.add(Payment.builder().fileNo("lkkaaw" + i).order(i).build());
        }
        DATA_A.remove(DATA_A.size() - 1);
        DATA_A.add(Payment.builder().fileNo("aa").order(1).build());
        DATA_B.remove(0);
        DATA_B.remove(0);
        DATA_B.remove(0);
        DATA_B.add(Payment.builder().fileNo("cc").order(6).build());
    }

    public Payment hashMap2() {
        return null;
    }
    public List<Payment> hashMap() {
        List<Payment> DATA_A = new ArrayList<>();
        List<Payment> DATA_B = new ArrayList<>();
        f(DATA_A, DATA_B);

        ConcurrentHashMap<Integer, Payment> dataSetA = getMap(DATA_A);
        ConcurrentHashMap<Integer, Payment> dataSetB = getMap(DATA_B);

        List<Payment> agreement = dataSetA.keySet()
                .parallelStream()
                .filter(key -> dataSetB.get(key) == null)
                .map(dataSetA::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Payment> reverseAgreement = dataSetB.keySet()
                .parallelStream()
                .filter(key -> dataSetA.get(key) == null)
                .map(dataSetB::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return agreement;
    }

    private void slepp() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private <V> ConcurrentHashMap<Integer, V> getMap(List<V> entityList) {
        return entityList
                .parallelStream()
                .collect(Collectors
                        .toConcurrentMap(
                                Object::hashCode,
                                Function.identity(),
                                (u, v) -> {
                                    log.trace("" + u.hashCode());
                                    return u;
                                },
                                ConcurrentHashMap::new
                        ));
    }

    public List<Payment> getegg() {
        return null;
    }

    public List<Payment> parallelStream() {
        long start = System.currentTimeMillis();

        List<Payment> DATA_A = new ArrayList<>();
        List<Payment> DATA_B = new ArrayList<>();
        f(DATA_A, DATA_B);

        List<Payment> collect = DATA_A.parallelStream()
                .filter(payment -> DATA_B.parallelStream()
                        .noneMatch(payment1 -> payment1.equals(payment)))
                .collect(Collectors.toList());

        return collect;
    }

}

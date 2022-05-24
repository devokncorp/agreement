
package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.core.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class TestX {

    private List<Payment> sourceDbResults = new ArrayList<>();//Arrays.asList(1, 2, 3, 4, 5, 8);
    private List<Payment> hiveResults = new ArrayList<>(); //Arrays.asList(2, 3, 6, 7);

//    private DDD userRepository = Mockito.mock(DDD.class);
    @Mock
    private AService aService;

    @BeforeEach
    public void c() {
        int MAX = 3_000_000;
        for (int i = 0; i < MAX; i++) {
            sourceDbResults.add(Payment.builder().fileNo("lkkaaw" + i).order(i).build());
            hiveResults.add(Payment.builder().fileNo("lkkaaw" + i).order(i).build());
        }
        sourceDbResults.remove(sourceDbResults.size() - 1);
        sourceDbResults.add(Payment.builder().fileNo("aa").order(1).build());
        hiveResults.remove(0);
        hiveResults.remove(0);
        hiveResults.add(Payment.builder().fileNo("cc").order(6).build());
    }

    @Test
    @Timeout(value = 3)
    public void xx() {
//        long start = System.currentTimeMillis();
//        when(aService.hashMap()).thenReturn(sourceDbResults);
//        when(aService.getegg()).thenReturn(hiveResults);
//
//        ConcurrentHashMap<Integer, Payment> p11 = getIntegerPaymentConcurrentHashMap(aService.hashMap());
//        ConcurrentHashMap<Integer, Payment> p22 = getIntegerPaymentConcurrentHashMap(aService.getegg());
//
//        List<Payment> agreement = p11.keySet().parallelStream().filter(key -> p22.get(key) == null).map(p11::get).filter(Objects::nonNull).collect(Collectors.toList());
//        List<Payment> reverseAgreement = p22.keySet().parallelStream().filter(key -> p11.get(key) == null).map(p22::get).filter(Objects::nonNull).collect(Collectors.toList());
//
////        List<Payment> agreement = agr.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());
////        List<Payment> reverseAgreement = reverseAgr.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());
//
//        assertEquals(agreement.size(), 3);
//        assertEquals(reverseAgreement.size(), 2);
//
//        log.debug("{}",(System.currentTimeMillis()-start));
    }

    private <U> ConcurrentHashMap getIntegerPaymentConcurrentHashMap(List<U> entityList) {
        return  entityList
                .parallelStream()
                .collect(Collectors
                        .toConcurrentMap(entity ->
                                        entity.hashCode(),
                                        Function.identity(),
                                        (u, v) -> u,
                                        () -> new ConcurrentHashMap(entityList.size())));
    }
}

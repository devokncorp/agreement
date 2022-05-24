package com.example.demo.controller;

import com.example.demo.controller.request.AggrementReuqest;
import com.example.demo.aggrement.payment.PaymentAgreement;
import com.example.demo.core.entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PaymentAgreement paymentAgreement;

    @GetMapping(value = "/hashMap")
    public Collection<Payment> getasdasd(@RequestBody AggrementReuqest request) {

        ObjectMapper objectMapper =new ObjectMapper();

        return paymentAgreement.run(request);
    }

    @GetMapping(value = "/h")
    public ResponseEntity<String> SAS() {
        List<Payment> sourceDbResults = new ArrayList<>();//Arrays.asList(1, 2, 3, 4, 5, 8);
//        List<Payment> hiveResults = new ArrayList<>(); //Arrays.asList(2, 3, 6, 7);
//        int MAX = 1_000_000;
        int MAX = 10_000;
        for (int i = 0; i < MAX; i++) {
            sourceDbResults.add(Payment.builder().fileNo("lkkaaw" + i).order(Double.valueOf(Math.ceil(Math.random()*100000)).intValue()+i).build());
//            hiveResults.add(Payment.builder().fileNo("lkkaaw" + i).order(i).build());
        }
        sourceDbResults.remove(sourceDbResults.size() - 1);
        sourceDbResults.add(Payment.builder().fileNo("aa").order(1).build());

        String s=null;
        long start = System.currentTimeMillis();
        log.error("{}", start);
        try {
            s = new ObjectMapper().writeValueAsString(sourceDbResults);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.error("{}", System.currentTimeMillis()-start);
        start = System.currentTimeMillis();
        try {
            List map = new ObjectMapper().readValue(s, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.error("{}", System.currentTimeMillis()-start);

        return new ResponseEntity<String>(HttpStatus.OK);
    }
}

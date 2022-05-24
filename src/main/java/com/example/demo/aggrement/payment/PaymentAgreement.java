package com.example.demo.aggrement.payment;


import com.example.demo.controller.request.AggrementReuqest;
import com.example.demo.core.base.OvmAgreement;
import com.example.demo.core.entity.Payment;
import com.example.demo.core.process.collector.PaymentDataCollector;
import com.example.demo.core.process.postprocess.PaymentPostProcessor;
import com.example.demo.core.process.preprocess.PaymentPreProcessor;
import org.springframework.stereotype.Component;

@Component
public class PaymentAgreement extends OvmAgreement<Integer, AggrementReuqest, Payment> {
    public PaymentAgreement(PaymentDataCollector collector, PaymentPreProcessor preProcessor, PaymentPostProcessor postProcessor) {
        super(collector, preProcessor, postProcessor, (e) -> e.hashCode());
    }

}
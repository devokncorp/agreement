package com.example.demo.core.process.preprocess;


import com.example.demo.core.base.BasePreProcessor;
import com.example.demo.core.entity.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentPreProcessor extends BasePreProcessor<Payment> {
    @Override
    public Payment processAllianzEntity(Payment p1) {
        return p1;
    }

    @Override
    public Payment processSbmEntity(Payment p1) {
        return p1;
    }
}
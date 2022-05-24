package com.example.demo.core.process.postprocess;


import com.example.demo.core.base.BasePostProcessor;
import com.example.demo.core.entity.Payment;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PaymentPostProcessor extends BasePostProcessor<Payment> {

    @Override
    public Payment postProcess(Payment payment) {
        return payment;
    }

    @Override
    public void onPostProcessEnds(Collection<Payment> t) {

    }
}

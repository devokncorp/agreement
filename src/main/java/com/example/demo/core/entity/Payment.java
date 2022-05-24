package com.example.demo.core.entity;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Builder
@Setter
@EqualsAndHashCode
public class Payment implements IOvmEntity {
    private String fileNo;
    private Integer order;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(fileNo, payment.fileNo) && Objects.equals(order, payment.order);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (fileNo == null ? 0 : fileNo.hashCode());
        hash = 31 * hash + (order.hashCode());
        return hash;

//        return Objects.hash(fileNo, order);
    }
}
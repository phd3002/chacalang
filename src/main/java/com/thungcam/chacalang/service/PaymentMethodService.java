package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> findAllActive();

    PaymentMethod findById(Long id);

    PaymentMethod findByNameIgnoreCase(String name);
}

package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.PaymentMethod;
import com.thungcam.chacalang.repository.PaymentMethodRepository;
import com.thungcam.chacalang.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> findAllActive() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod findById(Long id) {
        return paymentMethodRepository.getReferenceById(id);
    }

    @Override
    public PaymentMethod findByNameIgnoreCase(String name) {
        return paymentMethodRepository.findByNameIgnoreCase(name);
    }
}

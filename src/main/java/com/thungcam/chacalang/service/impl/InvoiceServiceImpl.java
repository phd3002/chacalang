package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.PaymentStatus;
import com.thungcam.chacalang.repository.InvoiceRepository;
import com.thungcam.chacalang.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public void createFromOrder(Orders order) {
    }
}


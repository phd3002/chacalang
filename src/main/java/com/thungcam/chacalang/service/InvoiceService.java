package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Orders;

public interface InvoiceService {
    void createFromOrder(Orders order);
}


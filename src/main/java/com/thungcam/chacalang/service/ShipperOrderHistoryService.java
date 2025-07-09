package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.OrderShipper;

import java.time.LocalDateTime;
import java.util.List;

public interface ShipperOrderHistoryService {
    List<OrderShipper> getOrderHistory(Long shipperId, LocalDateTime fromDate, LocalDateTime toDate);
}


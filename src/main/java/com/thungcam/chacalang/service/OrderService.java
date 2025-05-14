package com.thungcam.chacalang.service;

import com.thungcam.chacalang.dto.OrderCheckoutDTO;
import com.thungcam.chacalang.entity.Orders;

public interface OrderService {
    Orders createOrder(OrderCheckoutDTO checkoutDTO);
    Orders getOrderById(Long id);
    Orders getOrderByOrderCode(String orderCode);
    void updateOrderStatus(Long orderId, String status);
} 
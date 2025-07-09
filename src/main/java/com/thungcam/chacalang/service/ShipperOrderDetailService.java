package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.enums.OrderStatus;

import java.util.List;

public interface ShipperOrderDetailService {
    OrderShipper getOrderShipperDetail(Long orderId, Long shipperId);

    List<OrderItem> getOrderItems(Long orderId);

    void updateOrderStatus(Long orderId, Long shipperId, OrderStatus newStatus, String failReason);
}


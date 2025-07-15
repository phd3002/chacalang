package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.enums.OrderStatus;

import java.util.List;

public interface ShipperAssignedOrderService {
    List<OrderShipper> getAssignedOrders(Long shipperId, Long branchId);

    void updateOrderStatus(Long orderId, Long shipperId, OrderStatus status, String failReason);

}

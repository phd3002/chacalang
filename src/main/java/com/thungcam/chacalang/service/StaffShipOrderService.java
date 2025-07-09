package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StaffShipOrderService {
    List<Orders> getShipOrders(Long branchId, String search, OrderStatus status, LocalDate dateFrom, LocalDate dateTo, int page, int size);

    Map<OrderStatus, Long> getShipOrderStats(Long branchId);

    Orders getOrderDetail(Long orderId);

    boolean updateOrderStatus(Long orderId, OrderStatus status);

    boolean updateOrderNote(Long orderId, String note);

    long getTotalPages(Long branchId, String search, OrderStatus status, LocalDate dateFrom, LocalDate dateTo, int size);

    List<OrderItem> getOrderItems(Long orderId);

    Invoice getOrderInvoice(Long orderId);

    void assignShipperToOrder(Long orderId, Long shipperId);

    List<User> getShippersByBranch(Long branchId);
}

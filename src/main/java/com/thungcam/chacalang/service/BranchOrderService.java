package com.thungcam.chacalang.service;

import com.thungcam.chacalang.dto.OrderViewDTO;
import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;

import java.util.List;

public interface BranchOrderService {

    List<OrderViewDTO> getOrdersByBranch(Long branchId);

    void updateOrderStatus(Long orderId, OrderStatus newStatus);

    Orders getOrderById(Long id);

    List<OrderItem> getOrderItems(Long orderId);

    Invoice getInvoiceByOrderId(Long orderId);
}

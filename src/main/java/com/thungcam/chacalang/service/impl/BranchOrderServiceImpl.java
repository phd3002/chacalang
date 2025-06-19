package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.dto.OrderViewDTO;
import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.InvoiceRepository;
import com.thungcam.chacalang.repository.OrderItemRepository;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.service.BranchOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BranchOrderServiceImpl implements BranchOrderService {
    private final OrderRepository orderRepository;

    private final InvoiceRepository invoiceRepository;

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderViewDTO> getOrdersByBranch(Long branchId) {
        List<Orders> orders = orderRepository.findAllByBranch_IdOrderByCreatedAtDesc(branchId);
        return orders.stream().map(order -> {
            BigDecimal total = order.getInvoice() != null ? order.getInvoice().getFinalAmount() : BigDecimal.ZERO;
            return new OrderViewDTO(
                    order.getId(),
                    order.getOrderCode(),
                    order.getCustomerName(),
                    order.getCreatedAt(),
                    order.getStatus(),
                    total,
                    order.getShippingMethod()
            );
        }).toList();
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy đơn hàng với ID: " + id));
    }

    @Override
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public Invoice getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy hóa đơn với orderId = " + orderId));
    }
}

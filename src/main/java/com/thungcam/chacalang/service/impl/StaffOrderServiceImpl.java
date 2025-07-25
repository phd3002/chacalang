package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.enums.PaymentStatus;
import com.thungcam.chacalang.enums.ShippingMethod;
import com.thungcam.chacalang.repository.InvoiceRepository;
import com.thungcam.chacalang.repository.OrderItemRepository;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.service.MailService;
import com.thungcam.chacalang.service.StaffOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StaffOrderServiceImpl implements StaffOrderService {
    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final InvoiceRepository invoiceRepository;

    private final MailService mailService;

    @Override
    public List<Orders> getPickupOrders(Long branchId, String search, OrderStatus status, LocalDate dateFrom, LocalDate dateTo, int page, int size) {
        List<Orders> all;
        if ((search == null || search.isBlank()) && status == null && dateFrom == null && dateTo == null) {
            // Lấy tất cả đơn pickup của chi nhánh
            all = orderRepository.findAllByShippingMethodAndBranch_IdOrderByCreatedAtDesc(ShippingMethod.PICKUP, branchId);
        } else {
            // Có điều kiện lọc
            LocalDateTime dateFromDt = dateFrom != null ? dateFrom.atStartOfDay() : null;
            LocalDateTime dateToDt = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
            String searchQuery = (search != null && !search.isBlank()) ? search.toLowerCase() : null;
            all = orderRepository.searchPickupOrdersByBranch(
                    ShippingMethod.PICKUP, branchId, status, searchQuery, dateFromDt, dateToDt
            );
        }
        int from = (page - 1) * size;
        int to = Math.min(from + size, all.size());
        if (from > to) return Collections.emptyList();
        return all.subList(from, to);
    }


    @Override
    public Map<OrderStatus, Long> getPickupOrderStats(Long branchId) {
        Map<OrderStatus, Long> stats = new LinkedHashMap<>();
        for (OrderStatus status : List.of(OrderStatus.PENDING, OrderStatus.CONFIRMED, OrderStatus.PREPARING, OrderStatus.COMPLETED, OrderStatus.CANCELLED)) {
            stats.put(status, orderRepository.countByShippingMethodAndStatusAndBranch_Id(ShippingMethod.PICKUP, status, branchId));
        }
        return stats;
    }


    @Override
    public Orders getOrderDetail(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public boolean updateOrderStatus(Long orderId, OrderStatus status) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return false;
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        if(status == OrderStatus.COMPLETED){
            Invoice invoice = invoiceRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new NoSuchElementException("Không tìm thấy hóa đơn với orderId = " + orderId));
            invoice.setPaymentStatus(PaymentStatus.PAID);
        } else if (status == OrderStatus.CANCELLED) {
            mailService.sendOrderCancellation(order, order.getNote());
        }
        return true;
    }

    @Override
    public boolean updateOrderNote(Long orderId, String note) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (note != null && note.trim().isEmpty()) {
            note = null;
        }
        if (order != null) {
            order.setNote(note);
            orderRepository.save(order);
        }
        return true;
    }

    @Override
    public long getTotalPages(Long branchId, String search, OrderStatus status, LocalDate dateFrom, LocalDate dateTo, int size) {
        List<Orders> all;
        if ((search == null || search.isBlank()) && status == null && dateFrom == null && dateTo == null) {
            all = orderRepository.findAllByShippingMethodAndBranch_IdOrderByCreatedAtDesc(ShippingMethod.PICKUP, branchId);
        } else {
            LocalDateTime dateFromDt = dateFrom != null ? dateFrom.atStartOfDay() : null;
            LocalDateTime dateToDt = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
            String searchQuery = (search != null && !search.isBlank()) ? search.toLowerCase() : null;
            all = orderRepository.searchPickupOrdersByBranch(
                    ShippingMethod.PICKUP, branchId, status, searchQuery, dateFromDt, dateToDt
            );
        }
        return (all.size() + size - 1) / size;
    }

    @Override
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public Invoice getOrderInvoice(Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy hóa đơn với orderId = " + orderId));
    }
}

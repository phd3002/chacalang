package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.*;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.enums.ShippingMethod;
import com.thungcam.chacalang.repository.*;
import com.thungcam.chacalang.service.StaffShipOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StaffShipOrderServiceImpl implements StaffShipOrderService {
    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final InvoiceRepository invoiceRepository;

    private final UserRepository userRepository;

    private final OrderShipperRepository orderShipperRepository;

    @Override
    public List<Orders> getShipOrders(Long branchId, String search, OrderStatus status, LocalDate dateFrom, LocalDate dateTo, int page, int size) {
        List<Orders> all;
        if ((search == null || search.isBlank()) && status == null && dateFrom == null && dateTo == null) {
            // Lấy tất cả đơn pickup của chi nhánh
            all = orderRepository.findAllByShippingMethodAndBranch_IdOrderByCreatedAtDesc(ShippingMethod.DELIVERY, branchId);
        } else {
            // Có điều kiện lọc
            LocalDateTime dateFromDt = dateFrom != null ? dateFrom.atStartOfDay() : null;
            LocalDateTime dateToDt = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
            String searchQuery = (search != null && !search.isBlank()) ? search.toLowerCase() : null;
            all = orderRepository.searchPickupOrdersByBranch(
                    ShippingMethod.DELIVERY, branchId, status, searchQuery, dateFromDt, dateToDt
            );
        }
        int from = (page - 1) * size;
        int to = Math.min(from + size, all.size());
        if (from > to) return Collections.emptyList();
        return all.subList(from, to);
    }


    @Override
    public Map<OrderStatus, Long> getShipOrderStats(Long branchId) {
        Map<OrderStatus, Long> stats = new LinkedHashMap<>();
        for (OrderStatus status : List.of(OrderStatus.PENDING, OrderStatus.CONFIRMED, OrderStatus.PREPARING, OrderStatus.ASSIGNED, OrderStatus.SHIPPING, OrderStatus.COMPLETED, OrderStatus.CANCELLED)) {
            stats.put(status, orderRepository.countByShippingMethodAndStatusAndBranch_Id(ShippingMethod.DELIVERY, status, branchId));
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
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean updateOrderNote(Long orderId, String note) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return false;
        order.setNote(note);
        orderRepository.save(order);
        return true;
    }

    @Override
    public long getTotalPages(Long branchId, String search, OrderStatus status, LocalDate dateFrom, LocalDate dateTo, int size) {
        List<Orders> all;
        if ((search == null || search.isBlank()) && status == null && dateFrom == null && dateTo == null) {
            all = orderRepository.findAllByShippingMethodAndBranch_IdOrderByCreatedAtDesc(ShippingMethod.DELIVERY, branchId);
        } else {
            LocalDateTime dateFromDt = dateFrom != null ? dateFrom.atStartOfDay() : null;
            LocalDateTime dateToDt = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
            String searchQuery = (search != null && !search.isBlank()) ? search.toLowerCase() : null;
            all = orderRepository.searchPickupOrdersByBranch(
                    ShippingMethod.DELIVERY, branchId, status, searchQuery, dateFromDt, dateToDt
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

    @Override
    public void assignShipperToOrder(Long orderId, Long shipperId) {
        if(orderShipperRepository.existsByOrder_Id(orderId)) {
            throw new RuntimeException("Đơn hàng đã được gán shipper!");
        }
        Orders order = orderRepository.findById(orderId).orElseThrow();
        User shipper = userRepository.findById(shipperId).orElseThrow();
        OrderShipper orderShipper = new OrderShipper();
        orderShipper.setOrder(order);
        orderShipper.setShipper(shipper);
        orderShipper.setAssignedAt(LocalDateTime.now());
        orderShipperRepository.save(orderShipper);

        // Cập nhật trạng thái đơn nếu muốn
        order.setStatus(OrderStatus.ASSIGNED);
        orderRepository.save(order);
    }

    @Override
    public List<User> getShippersByBranch(Long branchId) {
        return userRepository.findByBranch_IdAndRole_Id(branchId, 5L);
    }



}

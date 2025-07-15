package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderItemRepository;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.service.OrderQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Page<Orders> getOrdersByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }


    @Override
    public Orders findDetailByIdAndUser(Long orderId, User user) {
        Orders order = orderRepository.findByIdAndUser(orderId, user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // Force load items nếu cần (hoặc dùng fetch join trong repo)
        List<OrderItem> items = orderItemRepository.findByOrderWithMenu(order);
        order.setItems(items);

        return order;
    }

    @Override
    @Transactional
    public void cancelOrderByUser(Long orderId, String cancelReason, String username) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy đơn hàng"));
        // Chỉ cho phép hủy khi trạng thái còn PENDING, CONFIRMED, PREPARING
        if (!(order.getStatus() == OrderStatus.PENDING
                || order.getStatus() == OrderStatus.CONFIRMED
                || order.getStatus() == OrderStatus.PREPARING)) {
            throw new IllegalStateException("Đơn hàng không thể hủy ở trạng thái hiện tại");
        }
        if (!order.getUser().getEmail().equals(username)) {
//            System.out.println("User: " + username + ", Order User: " + order.getUser().getEmail());
            throw new IllegalStateException("Bạn không có quyền hủy đơn này!");
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.setNote((order.getNote() != null ? order.getNote() + " | " : "") + "[Hủy: " + cancelReason + "]");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

}


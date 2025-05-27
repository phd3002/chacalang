package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.repository.OrderItemRepository;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<Orders> findAllByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
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
}


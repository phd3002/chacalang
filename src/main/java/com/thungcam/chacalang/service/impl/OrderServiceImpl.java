package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.dto.OrderCheckoutDTO;
import com.thungcam.chacalang.entity.Menu;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.MenuRepository;
import com.thungcam.chacalang.repository.OrderItemRepository;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    @Transactional
    public Orders createOrder(OrderCheckoutDTO checkoutDTO) {
        // Create new order
        Orders order = new Orders();
        order.setOrderCode(generateOrderCode());
        order.setCustomerName(checkoutDTO.getCustomerName());
        order.setCustomerPhone(checkoutDTO.getCustomerPhone());
        order.setCustomerEmail(checkoutDTO.getCustomerEmail());
        order.setCustomerAddress(checkoutDTO.getCustomerAddress());
        order.setNote(checkoutDTO.getNote());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Save order first to get the ID
        order = orderRepository.save(order);

        // Create order items
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : checkoutDTO.getCartData().entrySet()) {
            Long menuId = entry.getKey();
            Integer quantity = entry.getValue();

            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new RuntimeException("Menu not found: " + menuId));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenu(menu);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(menu.getPrice());
            orderItem.setCreatedAt(LocalDateTime.now());
            orderItem.setUpdatedAt(LocalDateTime.now());

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(menu.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        // Save order items
        orderItemRepository.saveAll(orderItems);

        // Update order total amount
        order.setTotalPrice(totalAmount);
        return orderRepository.save(order);
    }

    @Override
    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    @Override
    public Orders getOrderByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderCode));
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        Orders order = getOrderById(orderId);
        order.setStatus(OrderStatus.valueOf(status));
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    private String generateOrderCode() {
        return "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 
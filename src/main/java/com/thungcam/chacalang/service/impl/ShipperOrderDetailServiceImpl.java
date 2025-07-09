package com.thungcam.chacalang.service.impl;
import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderShipperRepository;
import com.thungcam.chacalang.repository.OrderItemRepository;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.service.ShipperOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperOrderDetailServiceImpl implements ShipperOrderDetailService {

    private final OrderShipperRepository orderShipperRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderShipper getOrderShipperDetail(Long orderId, Long shipperId) {
        return orderShipperRepository.findByOrder_IdAndShipper_Id(orderId, shipperId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn này cho shipper!"));
    }

    @Override
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrder_Id(orderId);
    }

    @Override
    public void updateOrderStatus(Long orderId, Long shipperId, OrderStatus newStatus, String failReason) {
        OrderShipper os = orderShipperRepository.findByOrder_IdAndShipper_Id(orderId, shipperId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn này!"));
        Orders order = os.getOrder();

        switch (newStatus) {
            case SHIPPING -> {
                os.setPickedAt(LocalDateTime.now());
                order.setStatus(OrderStatus.SHIPPING);
            }
            case COMPLETED -> {
                os.setDeliveredAt(LocalDateTime.now());
                order.setStatus(OrderStatus.COMPLETED);
            }
            case FAILED, CANCELLED -> {
                os.setDeliveredAt(LocalDateTime.now());
                order.setStatus(OrderStatus.CANCELLED);
                os.setNote(failReason);
            }
        }
        orderRepository.save(order);
        orderShipperRepository.save(os);
    }
}


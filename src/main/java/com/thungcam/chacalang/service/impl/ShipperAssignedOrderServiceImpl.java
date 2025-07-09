package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.repository.OrderShipperRepository;
import com.thungcam.chacalang.service.ShipperAssignedOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipperAssignedOrderServiceImpl implements ShipperAssignedOrderService {

    private final OrderShipperRepository orderShipperRepository;

    private final OrderRepository orderRepository;

    @Override
    public List<OrderShipper> getAssignedOrders(Long shipperId, Long branchId) {
        // Lấy tất cả đơn SHIPPING được giao cho shipper này tại branch này
        return orderShipperRepository.findAssignedShippingOrdersByShipper(shipperId)
                .stream()
                .filter(os -> os.getOrder().getBranch().getId().equals(branchId))
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Long orderId, Long shipperId, OrderStatus status) {
        OrderShipper os = orderShipperRepository.findByOrder_IdAndShipper_Id(orderId, shipperId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hoặc bạn không được phép thao tác!"));
        Orders order = os.getOrder();

        if (status == OrderStatus.SHIPPING) {
            os.setPickedAt(LocalDateTime.now());
            order.setStatus(OrderStatus.SHIPPING);
        } else if (status == OrderStatus.COMPLETED) {
            os.setDeliveredAt(LocalDateTime.now());
            order.setStatus(OrderStatus.COMPLETED); // hoặc DELIVERED, tùy enum
        }
        orderRepository.save(order);
        orderShipperRepository.save(os);
    }

}


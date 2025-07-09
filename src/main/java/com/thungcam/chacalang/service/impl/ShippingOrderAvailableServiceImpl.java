package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.dto.ShipperOrderAvailableDTO;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.service.ShippingOrderAvailableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShippingOrderAvailableServiceImpl implements ShippingOrderAvailableService {

    private final OrderRepository ordersRepository;

    @Override
    public List<ShipperOrderAvailableDTO> getOrdersAvailable(Long branchId, String district, String ward, LocalDateTime fromDate, LocalDateTime toDate) {
        List<OrderStatus> statuses = List.of(OrderStatus.PREPARING, OrderStatus.CONFIRMED, OrderStatus.PENDING);
        List<Orders> orders = ordersRepository.findOrdersAvailableForShipper(branchId, statuses, district, ward, fromDate, toDate);

        return orders.stream().map(o -> {
            ShipperOrderAvailableDTO dto = new ShipperOrderAvailableDTO();
            dto.setOrderId(o.getId());
            dto.setOrderCode(o.getOrderCode());
            dto.setCustomerName(o.getCustomerName());
            dto.setCustomerPhone(o.getCustomerPhone());
            dto.setAddress(o.getCustomerAddress());
            dto.setDistrict(o.getDistrict());
            dto.setWard(o.getWard());
            dto.setCreatedAt(o.getCreatedAt());
            dto.setStatus(o.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}

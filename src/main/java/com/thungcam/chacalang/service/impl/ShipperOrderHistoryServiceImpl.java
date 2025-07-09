package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderShipperRepository;
import com.thungcam.chacalang.service.ShipperOrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperOrderHistoryServiceImpl implements ShipperOrderHistoryService {

    private final OrderShipperRepository orderShipperRepository;

    @Override
    public List<OrderShipper> getOrderHistory(Long shipperId, LocalDateTime fromDate, LocalDateTime toDate) {
        return orderShipperRepository.findHistoryOrders(
                shipperId,
                OrderStatus.COMPLETED,
                fromDate,
                toDate
        );
    }
}


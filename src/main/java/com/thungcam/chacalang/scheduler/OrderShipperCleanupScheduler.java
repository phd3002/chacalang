package com.thungcam.chacalang.scheduler;

import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderRepository;
import com.thungcam.chacalang.repository.OrderShipperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderShipperCleanupScheduler {

    private final OrderRepository orderRepository;

    private final OrderShipperRepository orderShipperRepository;

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupOrderShipper() {
        List<OrderStatus> completedStatuses = Arrays.asList(
                OrderStatus.COMPLETED,
                OrderStatus.CANCELLED,
                OrderStatus.FAILED
        );
        List<Long> doneOrderIds = orderRepository.findOrderIdsByStatusIn(completedStatuses);
        if (doneOrderIds != null && !doneOrderIds.isEmpty()) {
            orderShipperRepository.deleteByOrder_IdIn(doneOrderIds);
        }
    }
}


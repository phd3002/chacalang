package com.thungcam.chacalang.scheduler;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderShipperRepository;
import com.thungcam.chacalang.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderAutoCompleteScheduler {

    private final OrderShipperRepository orderShipperRepository;
    private final OrderRepository orderRepository;

    // Chạy mỗi 1 giờ
    @Scheduled(fixedDelay = 3600000)
    public void autoCompleteOrders() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(24);
        List<OrderShipper> deliveredList = orderShipperRepository.findDeliveredOrdersBefore(threshold);

        for (OrderShipper os : deliveredList) {
            if (os.getOrder().getStatus() == OrderStatus.DELIVERED) {
                os.getOrder().setStatus(OrderStatus.COMPLETED);
                os.getOrder().setUpdatedAt(LocalDateTime.now());
                orderRepository.save(os.getOrder());
            }
        }
    }


}



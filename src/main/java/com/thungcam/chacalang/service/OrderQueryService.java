package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import org.springframework.data.domain.Page;

public interface OrderQueryService {
    Page<Orders> getOrdersByUser(Long userId, int page, int size);

    Orders findDetailByIdAndUser(Long orderId, User user);

    void cancelOrderByUser(Long orderId, String cancelReason, String username);

}

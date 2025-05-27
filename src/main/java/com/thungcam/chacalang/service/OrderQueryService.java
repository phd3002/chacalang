package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;

import java.util.List;

public interface OrderQueryService {
    List<Orders> findAllByUser(User user);

    Orders findDetailByIdAndUser(Long orderId, User user);
}

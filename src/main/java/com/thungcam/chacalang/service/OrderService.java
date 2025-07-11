package com.thungcam.chacalang.service;

import com.thungcam.chacalang.dto.OrderCheckoutDTO;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;

public interface OrderService {
    Orders createOrder(OrderCheckoutDTO dto, User user);

} 
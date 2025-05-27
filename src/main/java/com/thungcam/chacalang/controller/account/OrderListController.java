package com.thungcam.chacalang.controller.account;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.OrderQueryService;
import com.thungcam.chacalang.service.OrderService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class OrderListController {

    private final OrderQueryService orderQueryService;
    private final UserService userService;

    @GetMapping("/orders")
    public String listOrders(Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        model.addAttribute("user", user);
        List<Orders> orders = orderQueryService.findAllByUser(user);
        model.addAttribute("orders", orders);
        return "profile/order-list";
    }
}

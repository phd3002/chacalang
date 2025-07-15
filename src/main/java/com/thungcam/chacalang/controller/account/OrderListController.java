package com.thungcam.chacalang.controller.account;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.OrderQueryService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class OrderListController {

    private final OrderQueryService orderQueryService;
    private final UserService userService;

    @GetMapping("/orders")
    public String listOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model, Authentication authentication
    ) {
        User user = userService.getAuthenticatedUser(authentication);
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        model.addAttribute("user", user);
        Page<Orders> orderPage = orderQueryService.getOrdersByUser(user.getId(), page, size);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        return "profile/order-list";
    }
}

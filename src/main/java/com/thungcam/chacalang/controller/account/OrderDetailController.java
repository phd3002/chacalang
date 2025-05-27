package com.thungcam.chacalang.controller.account;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.OrderQueryService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderQueryService orderQueryService;
    private final UserService userService;

    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        model.addAttribute("user", user);
        Orders order = orderQueryService.findDetailByIdAndUser(id, user);
        model.addAttribute("order", order);
        return "profile/order-detail";
    }
}

package com.thungcam.chacalang.controller.branchManager.account;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.OrderQueryService;
import com.thungcam.chacalang.service.ReviewService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderQueryService orderQueryService;
    private final UserService userService;
    private final ReviewService reviewService;

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

    @PostMapping("/order-review")
    public String submitReview(
            @RequestParam("orderId") Long orderId,
            @RequestParam("rating") int rating,
            @RequestParam("comment") String comment,
            Principal principal, RedirectAttributes redirectAttributes
    ) {
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);

        reviewService.createReview(user, orderId, rating, comment);

        redirectAttributes.addFlashAttribute("success", AuthConst.MESSAGE.RATE_SUCCESS);
        return "redirect:/user/orders";
    }

    @PostMapping("/order-cancel")
    public String cancelOrder(
            @RequestParam("orderId") Long orderId,
            @RequestParam(value = "cancelReason", required = false) String cancelReason,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
            orderQueryService.cancelOrderByUser(orderId, cancelReason, authentication.getName());
            redirectAttributes.addFlashAttribute("success", AuthConst.MESSAGE.ORDER_CANCEL_SUCCESS);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", AuthConst.ERROR.ERROR);
        }
        return "redirect:/user/orders";
    }


}

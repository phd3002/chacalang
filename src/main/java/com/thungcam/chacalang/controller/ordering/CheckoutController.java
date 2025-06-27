package com.thungcam.chacalang.controller.ordering;

import com.thungcam.chacalang.dto.OrderCheckoutDTO;
import com.thungcam.chacalang.entity.*;
import com.thungcam.chacalang.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/order")
public class CheckoutController {
    private final CartService cartService;

    private final PaymentMethodService paymentMethodService;

    private final UserService userService;

    private final UserAddressService userAddressService;

    private final OrderService orderService;

    private final BranchService branchService;

    @GetMapping("/checkout")
    public String checkoutPage(Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        List<CartItem> cartItems = cartService.getCartItems(user.getId());
        List<PaymentMethod> paymentMethods = paymentMethodService.findAllActive();
        List<UserAddress> addresses = userAddressService.getAddressesByUserId(user);
        System.out.println("Addresses: " + addresses.toString());
        List<Branch> branches = branchService.getAllBranches();

        BigDecimal subtotal = cartItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal shippingFee = new BigDecimal("10000");
        BigDecimal total = subtotal.add(shippingFee);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("total", total);
        model.addAttribute("paymentMethods", paymentMethods);
        model.addAttribute("addresses", addresses);
        model.addAttribute("branches", branches); // Thymeleaf dùng "stores" trong checkout.html
        model.addAttribute("checkoutRequest", new OrderCheckoutDTO());

        return "order/checkout";
    }


    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute OrderCheckoutDTO dto,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(userDetails.getUsername());
        log.info("Processing checkout for user: {}", user.getEmail());
        Orders order = orderService.createOrder(dto, user);
        redirectAttributes.addFlashAttribute("success", order.getOrderCode());
        return "redirect:/order/success";
    }
}


package com.thungcam.chacalang.controller.order;

import com.thungcam.chacalang.dto.CartItemDTO;
import com.thungcam.chacalang.service.CartService;
import com.thungcam.chacalang.service.UserService;
import org.hibernate.StaleObjectStateException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/cart/add")
    @ResponseBody
    public String addToCart(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam Long menuId,
                            @RequestParam(defaultValue = "1") int quantity) {
        Long userId = getUserId(userDetails);
        cartService.addToCart(userId, menuId, quantity);
        return "OK";
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public String updateCart(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam Long menuId,
                             @RequestParam int quantity) {
        Long userId = getUserId(userDetails);
        cartService.updateQuantity(userId, menuId, quantity);
        return "OK";
    }

    @GetMapping("/api/cart")
    @ResponseBody
    public List<CartItemDTO> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return List.of(); // Trả về danh sách rỗng
        }
        Long userId = getUserId(userDetails);
        return cartService.getCartItems(userId).stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());
    }

//    @PostMapping("/cart/remove")
//    @ResponseBody
//    @Transactional
//    public String removeFromCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long menuId) {
//        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
//        cartService.removeFromCart(userId, menuId);
//        return "OK";
//    }

    @PostMapping("/cart/remove")
    @ResponseBody
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String removeFromCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long menuId) {
        try {
            Long userId = userService.findByEmail(userDetails.getUsername()).getId();
            cartService.removeFromCart(userId, menuId);
            return "OK";
        } catch (StaleObjectStateException e) {
            return "Item was already removed";
        }
    }




    private Long getUserId(UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername()).getId();
    }
}


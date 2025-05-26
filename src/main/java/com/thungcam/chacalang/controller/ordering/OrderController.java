package com.thungcam.chacalang.controller.ordering;

import com.thungcam.chacalang.dto.OrderCheckoutDTO;
import com.thungcam.chacalang.entity.Category;
import com.thungcam.chacalang.entity.Menu;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    final private CategoryService categoryService;

    final private MenuService menuService;

    final private OrderService orderService;

    final private UserService userService;

    final private CartService cartService;

    @GetMapping("/home")
    public String orderHomepage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        Map<Long, List<Menu>> menusByCategory = new HashMap<>();
        for (Category category : categories) {
            menusByCategory.put(category.getId(), menuService.getMenusByCategory(category.getId()));
        }
        model.addAttribute("menusByCategory", menusByCategory);

        // ✅ Load giỏ hàng nếu user đã login
        if (userDetails != null) {
            Long userId = userService.findByEmail(userDetails.getUsername()).getId();
            model.addAttribute("cartItems", cartService.getCartItems(userId));
            model.addAttribute("total", cartService.calculateTotal(userId));
        }

        return "order/order-homepage";
    }

    @GetMapping("/success")
    public String orderSuccess() {
        return "order/order-success";
    }
}
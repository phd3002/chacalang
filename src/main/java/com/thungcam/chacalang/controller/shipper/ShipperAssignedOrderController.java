package com.thungcam.chacalang.controller.shipper;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.service.ShipperAssignedOrderService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperAssignedOrderController {

    private final ShipperAssignedOrderService shipperAssignedOrderService;

    private final UserService userService;

    @GetMapping("/orders-assigned")
    public String assignedOrders(
            @RequestParam("branchId") Long branchId,
            Model model,
            Authentication authentication
    ) {
        String email = authentication.getName();
        User shipper = userService.findByEmail(email);

        List<OrderShipper> assignedOrders = shipperAssignedOrderService.getAssignedOrders(shipper.getId(), branchId);

        model.addAttribute("assignedOrders", assignedOrders);
        model.addAttribute("branchId", branchId);
        model.addAttribute("activePage", "orders-assigned");
        return "shipper/shipper-orders-assigned";
    }

    @PostMapping("/orders-assigned/start-shipping")
    public String startShipping(@RequestParam("orderId") Long orderId, Authentication authentication, RedirectAttributes redirectAttributes) {
        User shipper = userService.findByEmail(authentication.getName());
        try {
            shipperAssignedOrderService.updateOrderStatus(orderId, shipper.getId(), OrderStatus.SHIPPING);
            redirectAttributes.addFlashAttribute("success", "Nhận giao thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể nhận giao: " + e.getMessage());
        }
        return "redirect:/shipper/orders-assigned?branchId=" + shipper.getBranch().getId();
    }

    @PostMapping("/orders-assigned/complete")
    public String completeOrder(@RequestParam("orderId") Long orderId, Authentication authentication, RedirectAttributes redirectAttributes) {
        User shipper = userService.findByEmail(authentication.getName());
        try {
            shipperAssignedOrderService.updateOrderStatus(orderId, shipper.getId(), OrderStatus.DELIVERED);
            redirectAttributes.addFlashAttribute("success", "Đơn hàng đã giao thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể hoàn thành đơn: " + e.getMessage());
        }
        return "redirect:/shipper/orders-assigned?branchId=" + shipper.getBranch().getId();
    }

    @PostMapping("/orders-assigned/failed")
    public String failOrder(@RequestParam("orderId") Long orderId, Authentication authentication, RedirectAttributes redirectAttributes) {
        User shipper = userService.findByEmail(authentication.getName());
        try {
            shipperAssignedOrderService.updateOrderStatus(orderId, shipper.getId(), OrderStatus.FAILED);
            redirectAttributes.addFlashAttribute("success", "Đơn hàng đã giao thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể hoàn thành đơn: " + e.getMessage());
        }
        return "redirect:/shipper/orders-assigned?branchId=" + shipper.getBranch().getId();
    }

}


package com.thungcam.chacalang.controller.shipper;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.service.ShipperOrderDetailService;
import com.thungcam.chacalang.service.UserService;
import com.thungcam.chacalang.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperOrderDetailController {

    private final ShipperOrderDetailService shipperOrderDetailService;
    private final UserService userService;

    @GetMapping("/order-detail")
    public String detail(
            @RequestParam("orderId") Long orderId,
            @RequestParam("branchId") Long branchId,
            Model model,
            Authentication authentication
    ) {
        User shipper = userService.findByEmail(authentication.getName());
        OrderShipper os = shipperOrderDetailService.getOrderShipperDetail(orderId, shipper.getId());
        List<OrderItem> items = shipperOrderDetailService.getOrderItems(orderId);

        model.addAttribute("orderShipper", os);
        model.addAttribute("orderItems", items);
        model.addAttribute("branchId", branchId);
        model.addAttribute("activePage", "orders-assigned");
        return "shipper/shipper-order-detail";
    }

    @PostMapping("/order-status-update")
    public String updateStatus(
            @RequestParam("orderId") Long orderId,
            @RequestParam("action") String action,
            @RequestParam(value = "failReason", required = false) String failReason,
            Authentication authentication,
            @RequestParam("branchId") Long branchId
    ) {
        User shipper = userService.findByEmail(authentication.getName());

        OrderStatus newStatus = switch (action) {
            case "picked" -> OrderStatus.SHIPPING;
            case "delivered" -> OrderStatus.DELIVERED;
            case "failed" -> OrderStatus.FAILED;
            default -> throw new RuntimeException("Hành động không hợp lệ!");
        };

        shipperOrderDetailService.updateOrderStatus(orderId, shipper.getId(), newStatus, failReason);
        return "redirect:/shipper/orders-assigned?branchId=" + branchId;
    }
}


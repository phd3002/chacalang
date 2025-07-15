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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            @RequestParam("branchId") Long branchId,
            RedirectAttributes redirectAttributes
    ) {
        User shipper = userService.findByEmail(authentication.getName());
        try {
            OrderStatus newStatus = switch (action) {
                case "picked" -> OrderStatus.SHIPPING;
                case "delivered" -> OrderStatus.DELIVERED;
                case "failed" -> OrderStatus.FAILED;
                default -> throw new RuntimeException("Hành động không hợp lệ!");
            };
//            System.out.println("Updating order status to: " + newStatus);
            shipperOrderDetailService.updateOrderStatus(orderId, shipper.getId(), newStatus, failReason);

            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái đơn hàng thành công!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Cập nhật trạng thái thất bại: " + ex.getMessage());
        }
        return "redirect:/shipper/order-detail?orderId=" + orderId + "&branchId=" + branchId;
    }
}


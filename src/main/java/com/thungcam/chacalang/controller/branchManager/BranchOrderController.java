package com.thungcam.chacalang.controller.branchManager;

import com.thungcam.chacalang.dto.OrderViewDTO;
import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.service.BranchOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/branch-manager")
@RequiredArgsConstructor
public class BranchOrderController {

    private final BranchOrderService branchOrderService;

    @GetMapping("/branch-order-management")
    public String showOrderList(@RequestParam Long branchId, Model model) {
        List<OrderViewDTO> orders = branchOrderService.getOrdersByBranch(branchId);
        model.addAttribute("orders", orders);
        model.addAttribute("branchId", branchId);
        return "branch-manager/branch-order-management";
    }

    @PostMapping("/branch-order-management/update-status")
    public String updateOrderStatus(@RequestParam Long orderId,
                                    @RequestParam OrderStatus newStatus,
                                    @RequestParam Long branchId) {
        branchOrderService.updateOrderStatus(orderId, newStatus);
        return "redirect:/branch-manager/branch-order-management?branchId=" + branchId;
    }

    @GetMapping("/branch-order-detail-management")
    public String viewOrderDetail(@RequestParam Long orderId, Model model) {
        Orders order = branchOrderService.getOrderById(orderId);
        List<OrderItem> items = branchOrderService.getOrderItems(orderId);
        Invoice invoice = branchOrderService.getInvoiceByOrderId(orderId);

        model.addAttribute("order", order);
        model.addAttribute("orderItems", items);
        model.addAttribute("invoice", invoice);
        model.addAttribute("branchId", order.getBranch().getId());
        return "branch-manager/branch-order-detail-management";
    }

}

package com.thungcam.chacalang.controller.staff;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.service.StaffOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/staff")
@Transactional
@RequiredArgsConstructor
public class StaffOrderDetailController {
    private final StaffOrderService staffOrderService;

    @GetMapping("/orders/update/{orderId}")
    public String orderDetail(@PathVariable Long orderId,
                              @RequestParam Long branchId,
                              Model model) {
        Orders order = staffOrderService.getOrderDetail(orderId);
        List<OrderItem> items = staffOrderService.getOrderItems(orderId);
        Invoice invoice = staffOrderService.getOrderInvoice(orderId);

        model.addAttribute("order", order);
        model.addAttribute("items", items);
        model.addAttribute("invoice", invoice);
        model.addAttribute("branchId", branchId);
        if(order.getStatus() == OrderStatus.DELIVERED) {
            model.addAttribute("activePage", "order-ship");
        } else {
            model.addAttribute("activePage", "order");
        }
//        model.addAttribute("activePage", "order");
        return "staff/staff-order-detail";
    }

    // Xử lý cập nhật trạng thái/ghi chú
    @PostMapping("/orders/update/{orderId}")
    public String updateOrderDetail(@PathVariable Long orderId,
                                    @RequestParam String newStatus,
                                    @RequestParam(required = false) String note,
                                    @RequestParam(required = false) Long branchId,
                                    RedirectAttributes redirectAttributes) {
        staffOrderService.updateOrderStatus(orderId, OrderStatus.valueOf(newStatus));
        staffOrderService.updateOrderNote(orderId, note);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        System.out.println("Order ID: " + orderId + ", New Status: " + newStatus + ", Note: " + note);
        return "redirect:/staff/orders/update/" + orderId + "?branchId=" + branchId;
    }
}
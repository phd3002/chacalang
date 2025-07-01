package com.thungcam.chacalang.controller.staff;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.service.StaffOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffOrderController {

    private final StaffOrderService staffOrderService;

    @GetMapping("/orders")
    public String pickupOrders(
            @RequestParam(value = "branchId", required = false) Long branchId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @RequestParam(value = "dateTo", required = false) String dateTo,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        LocalDate fromDate = (dateFrom != null && !dateFrom.isBlank()) ? LocalDate.parse(dateFrom) : null;
        LocalDate toDate = (dateTo != null && !dateTo.isBlank()) ? LocalDate.parse(dateTo) : null;

        List<Orders> ordersList = staffOrderService.getPickupOrders(branchId, search, status, fromDate, toDate, page, size);
//        System.out.println("Orders List: " + ordersList.size() + " items");
//        System.out.println("Branch ID: " + branchId);
//        System.out.println("Search: " + search);
//        System.out.println("Status: " + (status != null ? status.name() : "null"));
//        System.out.println("Date From: " + fromDate);
//        System.out.println("Date To: " + toDate);
//        System.out.println("Page: " + page);
        Map<OrderStatus, Long> stats = staffOrderService.getPickupOrderStats(branchId);
//        System.out.println("Stats: " + stats);
        long totalPages = staffOrderService.getTotalPages(branchId, search, status, fromDate, toDate, size);
        model.addAttribute("orders", ordersList);
        model.addAttribute("stats", stats);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("param", new HashMap<String, Object>() {{
            put("search", search);
            put("status", status != null ? status.name() : "");
            put("dateFrom", dateFrom);
            put("dateTo", dateTo);
        }});
        model.addAttribute("activePage", "order");
        model.addAttribute("branchId", branchId);
        return "staff/staff-orders";
    }

    // Chi tiết đơn hàng (AJAX load modal)
//    @GetMapping("/orders/{orderId}/detail")
//    public String orderDetail(@PathVariable Long orderId, Model model) {
//        Orders order = staffOrderService.getOrderDetail(orderId);
//        List<OrderItem> items = staffOrderService.getOrderItems(orderId);
//        Invoice invoice = staffOrderService.getOrderInvoice(orderId);
//
//        model.addAttribute("order", order);
//        model.addAttribute("items", items);
//        model.addAttribute("invoice", invoice);
//        return "staff/staff-orders :: orderDetail";
//    }
//
//    // Cập nhật trạng thái đơn hàng
//    @PostMapping("/orders/{orderId}/status")
//    @ResponseBody
//    public Map<String, Object> updateOrderStatus(@PathVariable Long orderId, @RequestParam("status") OrderStatus status) {
//        boolean ok = staffOrderService.updateOrderStatus(orderId, status);
//        Map<String, Object> result = new HashMap<>();
//        result.put("success", ok);
//        result.put("message", ok ? "Cập nhật thành công" : "Không tìm thấy đơn");
//        return result;
//    }
//
//    @PostMapping("/orders/{orderId}/note")
//    @ResponseBody
//    public Map<String, Object> updateOrderNote(@PathVariable Long orderId, @RequestParam("note") String note) {
//        boolean ok = staffOrderService.updateOrderNote(orderId, note);
//        Map<String, Object> result = new HashMap<>();
//        result.put("success", ok);
//        result.put("message", ok ? "Cập nhật thành công" : "Không tìm thấy đơn");
//        return result;
//    }
}

package com.thungcam.chacalang.controller.staff;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.service.StaffShipOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffShipOrderController {
    private final StaffShipOrderService staffShipOrderService;

    @GetMapping("/orders-ship")
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

        List<Orders> ordersList = staffShipOrderService.getShipOrders(branchId, search, status, fromDate, toDate, page, size);
        Map<OrderStatus, Long> stats = staffShipOrderService.getShipOrderStats(branchId);
        long totalPages = staffShipOrderService.getTotalPages(branchId, search, status, fromDate, toDate, size);
        List<User> shippers = staffShipOrderService.getShippersByBranch(branchId);
        model.addAttribute("shippers", shippers);
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
        model.addAttribute("activePage", "order-ship");
        model.addAttribute("branchId", branchId);
        return "staff/staff-ship-order";
    }

    @PostMapping("/assign")
    public String assignShipper(
            @RequestParam("orderId") Long orderId,
            @RequestParam("shipperId") Long shipperId,
            @RequestParam("branchId") Long branchId,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        try {
            staffShipOrderService.assignShipperToOrder(orderId, shipperId);
            redirectAttributes.addFlashAttribute("success", "Đã phân công shipper thành công!");
            model.addAttribute("success", "Đã phân công shipper thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/staff/orders-ship?branchId=" + branchId;
    }

}

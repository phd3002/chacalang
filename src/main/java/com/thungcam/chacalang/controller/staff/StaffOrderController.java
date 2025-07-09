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
        Map<OrderStatus, Long> stats = staffOrderService.getPickupOrderStats(branchId);
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
}

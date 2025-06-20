package com.thungcam.chacalang.controller.branchManager;

import com.thungcam.chacalang.service.BranchStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BranchStatisticsController {

    private final BranchStatisticsService branchStatisticsService;

    @GetMapping("/branch-manager/branch-statistics")
    public String showStatistics(
            @RequestParam("branchId") Long branchId,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Model model) {
        LocalDateTime fromDateTime = fromDate != null ? fromDate.atStartOfDay() : LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime toDateTime = toDate != null ? toDate.atTime(23, 59, 59) : LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);

        // Validate "Từ ngày" không sau "Đến ngày"
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            model.addAttribute("dateError", "Từ ngày không được sau Đến ngày!");
            // Đổ lại trang với các biến cần thiết
            return "branch-manager/branch-statistics";
        }

        // Truyền xuống service dùng LocalDateTime
        Map<String, Object> statistics = branchStatisticsService.getBranchStatistics(branchId, fromDateTime, toDateTime);

        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        model.addAttribute("branchId", branchId);
        model.addAttribute("totalOrders", statistics.get("totalOrders"));
        model.addAttribute("totalRevenue", statistics.get("totalRevenue"));
        model.addAttribute("totalReservations", statistics.get("totalReservations"));
        model.addAttribute("cancelRate", statistics.get("cancelRate"));
        model.addAttribute("topMenu", statistics.get("topMenu"));

        // Dữ liệu cho JS biểu đồ
        model.addAttribute("ordersLabels", statistics.get("ordersLabels"));
        model.addAttribute("ordersValues", statistics.get("ordersValues"));
        model.addAttribute("revenueLabels", statistics.get("revenueLabels"));
        model.addAttribute("revenueValues", statistics.get("revenueValues"));
        model.addAttribute("topMenuLabels", statistics.get("topMenuLabels"));
        model.addAttribute("topMenuValues", statistics.get("topMenuValues"));

        return "branch-manager/branch-statistics";
    }
}

package com.thungcam.chacalang.controller.admin;

import com.thungcam.chacalang.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    @Autowired
    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalOrders", dashboardService.getTotalOrders());
        model.addAttribute("totalRevenue", dashboardService.getTotalRevenue());
        model.addAttribute("newCustomers", dashboardService.getNewCustomers());

        Map<String, BigDecimal> chart = dashboardService.getRevenueChartData();
        model.addAttribute("chartLabels", chart.keySet());
        model.addAttribute("chartData", chart.values());
        model.addAttribute("activePage", "dashboard");

        return "admin/dashboard";
    }
}


package com.thungcam.chacalang.controller.staff;

import com.thungcam.chacalang.dto.StaffDashboardDTO;
import com.thungcam.chacalang.service.StaffDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffDashboardController {
    private final StaffDashboardService staffDashboardService;

    @GetMapping("/staff-dashboard")
    public String dashboard(Model model, @RequestParam("branchId") Long branchId) {

        StaffDashboardDTO dto = staffDashboardService.getDashboardData(branchId);

        model.addAttribute("ordersNew", dto.getOrdersNew());
        model.addAttribute("ordersPreparing", dto.getOrdersPreparing());
        model.addAttribute("ordersShipping", dto.getOrdersShipping());
        model.addAttribute("ordersCompleted", dto.getOrdersCompleted());
        model.addAttribute("ordersCancelled", dto.getOrdersCancelled());
        model.addAttribute("todayRevenue", dto.getTodayRevenue());

        model.addAttribute("stockAlerts", dto.getStockAlerts());
        model.addAttribute("todayReservations", dto.getTodayReservations());
        model.addAttribute("notifications", dto.getNotifications());

        model.addAttribute("activePage", "dashboard");

        return "staff/staff-dashboard";
    }
}

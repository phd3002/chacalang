package com.thungcam.chacalang.controller.shipper;

import com.thungcam.chacalang.dto.ShipperDashboardDTO;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.ShippingDashboardService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperDashboardController {
    private final ShippingDashboardService shippingDashboardService;

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam("branchId") Long branchId,
                            Model model,
                            Authentication authentication) {
        String username = authentication.getName();
        User shipper = userService.findByEmail(username);
        ShipperDashboardDTO dashboard = shippingDashboardService.getShipperDashboard(shipper.getId(), branchId);
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("branchId", branchId);
        model.addAttribute("activePage", "dashboard_active");
        return "shipper/shipper-dashboard";
    }
}

package com.thungcam.chacalang.controller.branchManager;

import com.thungcam.chacalang.dto.BranchDashboardDTO;
import com.thungcam.chacalang.entity.BranchMenuStock;
import com.thungcam.chacalang.service.BranchDashboardService;
import com.thungcam.chacalang.service.BranchMenuStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/branch-manager")
@RequiredArgsConstructor
public class BranchDashboardController {
    private final BranchDashboardService branchDashboardService;

    private final BranchMenuStockService branchMenuStockService;

    @GetMapping("/branch-dashboard")
    public String showBranchDashboard(@RequestParam("branchId") Long branchId, Model model) {
        BranchDashboardDTO dashboard = branchDashboardService.getDashboardData(branchId);
        List<BranchMenuStock> outOfStockItems = branchMenuStockService.getOutOfStockMenuByBranch(branchId);
        model.addAttribute("outOfStockItems", outOfStockItems);
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("branchId", branchId);

        return "branch-manager/branch-dashboard"; // ánh xạ tới file: templates/branch/branch-dashboard.html
    }
}


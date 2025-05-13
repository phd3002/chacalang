package com.thungcam.chacalang.controller.ordering;

import com.thungcam.chacalang.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class SelectBranchController {
    final private BranchService branchService;

    @Autowired
    public SelectBranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // Example method to show branch selection page
    @GetMapping("/select-branch")
    public String selectBranch(Model model) {
        model.addAttribute("branches", branchService.getAllBranches());
        return "order/select-branch"; // Return the view name for branch selection
    }

    // Add more methods as needed for handling branch-related operations
}

package com.thungcam.chacalang.controller.branchManager;

import com.thungcam.chacalang.entity.BranchMenuStock;
import com.thungcam.chacalang.service.BranchMenuStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/branch-manager")
@RequiredArgsConstructor
public class BranchMenuController {

    private final BranchMenuStockService branchMenuStockService;

    @GetMapping("/branch-menu")
    public String viewBranchMenu(@RequestParam Long branchId, Model model) {
        List<BranchMenuStock> stockList = branchMenuStockService.getStockByBranch(branchId);
        model.addAttribute("branchId", branchId);
        model.addAttribute("stockList", stockList);
        return "branch-manager/branch-menu";
    }

    @PostMapping("/branch-menu/update-stock")
    public String updateStock(@RequestParam Long stockId,
                              @RequestParam int quantity,
                              @RequestParam Long branchId,
                              RedirectAttributes redirectAttributes) {

        if (quantity < 0 || quantity > 500) {
            redirectAttributes.addFlashAttribute("error", "Số lượng phải từ 0 đến 500");
            return "redirect:/branch-manager/branch-menu?branchId=" + branchId;
        }

        branchMenuStockService.updateQuantity(stockId, quantity);
        return "redirect:/branch-manager/branch-menu?branchId=" + branchId;
    }

}

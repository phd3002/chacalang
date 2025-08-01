package com.thungcam.chacalang.controller.branchManager;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.BranchStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/branch-manager")
@RequiredArgsConstructor
public class BranchStaffController {

    private final BranchStaffService branchStaffService;

    @GetMapping("/branch-staff-management")
    public String viewStaff(@RequestParam Long branchId, Model model) {
        List<User> staffList = branchStaffService.getStaffByBranch(branchId);
        List<User> shipperList = branchStaffService.getShipperByBranch(branchId);
        model.addAttribute("branchId", branchId);
        model.addAttribute("staffList", staffList);
        model.addAttribute("shipperList", shipperList);
        return "branch-manager/branch-staff-management";
    }

    @PostMapping("/branch-staff/update")
    public String updateStaff(@RequestParam Long id,
                              @RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String email,
                              @RequestParam String phone,
                              @RequestParam Long branchId) {
        branchStaffService.updateUserFields(id, firstName, lastName, email, phone);
        return "redirect:/branch-manager/branch-staff-management?branchId=" + branchId;
    }

    @PostMapping("/branch-staff/delete")
    public String deleteStaff(@RequestParam Long userId,
                              @RequestParam Long branchId,
                              RedirectAttributes redirectAttributes) {
        try {
            branchStaffService.deleteStaff(userId, branchId);
            redirectAttributes.addFlashAttribute("success", AuthConst.MESSAGE.DELETE_STAFF_SUCCESS);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/branch-manager/branch-staff-management?branchId=" + branchId;
    }


    @PostMapping("/branch-staff/toggle-status")
    public String toggleStatus(@RequestParam Long userId, @RequestParam Long branchId) {
        branchStaffService.toggleUserStatus(userId);
        return "redirect:/branch-manager/branch-staff-management?branchId=" + branchId;
    }

    @GetMapping("/branch-staff-create")
    public String showCreateForm(@RequestParam Long branchId,
                                 @RequestParam String role,
                                 Model model) {
        model.addAttribute("branchId", branchId);
        model.addAttribute("role", role);
        return "branch-manager/branch-staff-create";
    }

    @PostMapping("/branch-staff/create")
    public String createStaff(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String username,
                              @RequestParam String email,
                              @RequestParam String phone,
                              @RequestParam String password,
                              @RequestParam Long branchId,
                              @RequestParam String role
    ) {
        branchStaffService.createStaffOrShipper(branchId, firstName, lastName, username, email, phone, password, role);
        return "redirect:/branch-manager/branch-staff-management?branchId=" + branchId;
    }
}

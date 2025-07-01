package com.thungcam.chacalang.controller.staff;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffProfileController {

    private final UserService userService;

    // Hiển thị thông tin nhân viên
    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication authentication) {
        User staff = userService.getAuthenticatedUser(authentication);
        Long branchId = staff.getBranch() != null ? staff.getBranch().getId() : null;
//        System.out.println("Staff Profile: " + staff.toString());
        model.addAttribute("staff", staff);
        model.addAttribute("activePage", "profile");
        model.addAttribute("branchId", branchId);
        return "staff/staff-profile";
    }

    // Cập nhật thông tin (Chỉ cho sửa họ tên và số điện thoại)
    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String phone,
                                RedirectAttributes redirectAttributes,
                                Authentication authentication,
                                Model model) {
        User staff = userService.getAuthenticatedUser(authentication);
        User updated = userService.updateStaffProfile(staff.getUsername(), firstName, lastName, phone);
        model.addAttribute("staff", updated);
        model.addAttribute("activePage", "profile");
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        return "redirect:/staff/profile";
    }

    @PostMapping("/change-password-staff")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes,
                                 Authentication authentication,
                                 Model model) {
        User staff = userService.getAuthenticatedUser(authentication);
        model.addAttribute("staff", staff);
        model.addAttribute("activePage", "profile");
        try {
            userService.changePassword(authentication, currentPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("successMessage", AuthConst.MESSAGE.UPDATE_PASSWORD_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/staff/profile";
    }
}

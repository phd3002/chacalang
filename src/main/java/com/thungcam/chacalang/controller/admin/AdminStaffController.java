package com.thungcam.chacalang.controller.admin;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.BranchService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminStaffController {

    private final UserService userService;
    private final BranchService branchService;

    @GetMapping("/staff-management")
    public String listStaff(Model model) {
        model.addAttribute("staffList", userService.findAllStaff());
        model.addAttribute("activePage", "staff");
        return "admin/staff-management";
    }

    @GetMapping({"/create-or-edit-staff", "/create-or-edit-staff/{id}"})
    public String showForm(@PathVariable(required = false) Long id, Model model) {
        boolean isEdit = id != null;
        User staff = isEdit ? userService.findById(id) : new User();
        model.addAttribute("staff", staff);
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("activePage", "staff");
        model.addAttribute("isEdit", isEdit);
        return "admin/create-or-edit-staff";
    }

    @PostMapping("/save-staff")
    public String saveStaff(@ModelAttribute("staff") User staff) {
        userService.saveStaff(staff);
        staff.setCreatedAt(LocalDateTime.now());
        return "redirect:/admin/staff-management";
    }

    @DeleteMapping("/api/admin/staff/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        userService.deleteStaffById(id);
        return ResponseEntity.ok().build();
    }
}

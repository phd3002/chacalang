package com.thungcam.chacalang.controller.admin;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.BranchService;
import com.thungcam.chacalang.service.RoleService;
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
public class AdminUserController {

    private final UserService userService;
    private final BranchService branchService;
    private final RoleService roleService;

    @GetMapping("/user-management")
    public String listUser(Model model) {
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("activePage", "user");
        return "admin/user-management";
    }

    @GetMapping({"/create-or-edit-user", "/create-or-edit-user/{id}"})
    public String showForm(@PathVariable(required = false) Long id, Model model) {
        boolean isEdit = id != null;
        User user = isEdit ? userService.findById(id) : new User();
        model.addAttribute("user", user);
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("activePage", "user");
        model.addAttribute("isEdit", isEdit);
        return "admin/create-or-edit-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);

        return "redirect:/admin/user-management";
    }

    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}

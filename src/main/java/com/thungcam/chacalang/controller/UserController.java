package com.thungcam.chacalang.controller;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        model.addAttribute("user", user);
        return "profile/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(Authentication authentication,
                                @ModelAttribute("user") User updatedUser,
                                RedirectAttributes redirectAttributes) {
        try{
            userService.updateProfile(authentication, updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", AuthConst.MESSAGE.UPDATE_PROFILE_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật thông tin thất bại: " + e.getMessage());
        }
        return "redirect:/profile";
    }
}

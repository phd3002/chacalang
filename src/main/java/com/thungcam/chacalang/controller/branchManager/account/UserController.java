package com.thungcam.chacalang.controller.branchManager.account;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.UserAddressService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @GetMapping("/profile-manager")
    public String showProfileManage(Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        model.addAttribute("user", user);
        return "profile/profile-manage";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        model.addAttribute("user", user);
        return "profile/profile";
    }

    @GetMapping("/change-password")
    public String showChangePassword(Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        model.addAttribute("user", user);
        return "profile/change-password";
    }

    @PostMapping("/profile")
    public String updateProfile(Authentication authentication,
                                @ModelAttribute("user") User updatedUser,
                                RedirectAttributes redirectAttributes) {
        try{
            userService.updateProfile(authentication, updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", AuthConst.MESSAGE.UPDATE_PROFILE_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", AuthConst.ERROR.UPDATE_PROFILE_FAILED);
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(Authentication authentication,
                                 String currentPassword,
                                 String newPassword,
                                 String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.changePassword(authentication, currentPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("successMessage", AuthConst.MESSAGE.UPDATE_PASSWORD_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user/change-password";
    }

}

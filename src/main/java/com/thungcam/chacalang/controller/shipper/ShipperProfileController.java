package com.thungcam.chacalang.controller.shipper;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.service.ShipperProfileService;
import com.thungcam.chacalang.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperProfileController {

    private final ShipperProfileService shipperProfileService;

    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        User shipper = shipperProfileService.findByEmail(email);
        model.addAttribute("shipper", shipper);
        model.addAttribute("branchId", shipper.getBranch().getId());
        model.addAttribute("activePage", "profile");
        return "shipper/shipper-profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            Authentication authentication,
            Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        String email = authentication.getName();
        User user = shipperProfileService.findByEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        shipperProfileService.save(user);

        // Add thông báo thành công
        redirectAttributes.addFlashAttribute("success", AuthConst.MESSAGE.UPDATE_PROFILE_SUCCESS );
        return "redirect:/shipper/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        String email = authentication.getName();
        try {
            shipperProfileService.changePassword(email, oldPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("success", AuthConst.MESSAGE.UPDATE_PASSWORD_SUCCESS);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/shipper/profile";
    }
}

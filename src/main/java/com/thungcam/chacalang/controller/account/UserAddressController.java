package com.thungcam.chacalang.controller.account;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.UserAddress;
import com.thungcam.chacalang.service.UserAddressService;
import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserService userService;
    private final UserAddressService userAddressService;

    @GetMapping("/user-address")
    public String showUserAddresses(Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        model.addAttribute("addresses", userAddressService.getAllUserAddresses(user));
        return "profile/shipping-address";
    }

    @GetMapping("/update-address")
    public String showUpdateAddressForm(@RequestParam(required = false) Long id,
                                        Model model,
                                        Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        UserAddress address = (id != null)
                ? userAddressService.getUserAddressByIdAndUser(id, user)
                : new UserAddress();
        model.addAttribute("address", address);
        return "profile/update-shipping-address";
    }

    @PostMapping("/update-address")
    public String saveAddress(Authentication authentication,
                              @ModelAttribute UserAddress address,
                              @RequestParam("isDefault") boolean isDefault,
                              RedirectAttributes redirectAttributes) {
        User user = userService.getAuthenticatedUser(authentication);
        if (address.getId() == null) {
            userAddressService.createUserAddress(user, address, isDefault);
        } else {
            userAddressService.updateUserAddress(user, address, isDefault);
        }
        redirectAttributes.addFlashAttribute("success", "Lưu địa chỉ thành công");
        return "redirect:/user/user-address";
    }

    @PostMapping("/delete-address")
    public String deleteAddress(@RequestParam Long id,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        User user = userService.getAuthenticatedUser(authentication);
        userAddressService.deleteAddress(user, id);
        redirectAttributes.addFlashAttribute("success", "Xóa địa chỉ thành công");
        return "redirect:/user/user-address";
    }
}

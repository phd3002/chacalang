package com.thungcam.chacalang.controller.account;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.UserAddress;
import com.thungcam.chacalang.exception.BusinessException;
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
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        model.addAttribute("user", user);
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
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        model.addAttribute("user", user);
        model.addAttribute("address", address);
        model.addAttribute("addresses", userAddressService.getAllUserAddresses(user));
        return "profile/update-shipping-address";
    }

    @PostMapping("/update-address")
    public String saveAddress(Authentication authentication,
                              @ModelAttribute UserAddress address,
                              @RequestParam(value = "isDefault", required = false) Boolean isDefault,
                              RedirectAttributes redirectAttributes) {
        User user = userService.getAuthenticatedUser(authentication);
        try {
            Boolean isDefaultFlag = (address.getId() != null && address.getIsDefault()) || Boolean.TRUE.equals(isDefault);

            if (address.getId() == null) {
                userAddressService.createUserAddress(user, address, isDefaultFlag);
                redirectAttributes.addFlashAttribute("success", AuthConst.MESSAGE.SAVE_ADDRESS_SUCCESS);
            } else {
                userAddressService.updateUserAddress(user, address, isDefaultFlag);
                redirectAttributes.addFlashAttribute("success", AuthConst.MESSAGE.UPDATE_ADDRESS_SUCCESS);
            }
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/user/user-address";
    }




    @PostMapping("/delete-address")
    public String deleteAddress(@RequestParam Long id,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        User user = userService.getAuthenticatedUser(authentication);
        userAddressService.deleteAddress(user, id);
        redirectAttributes.addFlashAttribute("success", AuthConst.MESSAGE.DELETE_ADDRESS_SUCCESS);
        return "redirect:/user/user-address";
    }
}

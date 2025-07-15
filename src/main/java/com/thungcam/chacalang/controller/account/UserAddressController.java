package com.thungcam.chacalang.controller.account;

import com.thungcam.chacalang.constant.AuthConst;
import com.thungcam.chacalang.entity.District;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.UserAddress;
import com.thungcam.chacalang.entity.Ward;
import com.thungcam.chacalang.exception.BusinessException;
import com.thungcam.chacalang.service.DistrictService;
import com.thungcam.chacalang.service.UserAddressService;
import com.thungcam.chacalang.service.UserService;
import com.thungcam.chacalang.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserService userService;
    private final UserAddressService userAddressService;
    private final DistrictService districtService;
    private final WardService wardService;

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
    public String updateAddressForm(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long districtId,
            Model model,
            Authentication authentication
    ) {
        User user = userService.getAuthenticatedUser(authentication);
        String fullName = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("fullName", fullName);
        // Lấy danh sách quận huyện
        List<District> districts = districtService.getAllDistricts();
        model.addAttribute("districts", districts);

        // Lấy địa chỉ đang chỉnh sửa (nếu có)
        UserAddress address = (id != null) ? userAddressService.getAddressById(id) : new UserAddress();
        model.addAttribute("address", address);

        List<Ward> wards = new ArrayList<>();
        if (districtId != null) {
            wards = wardService.getWardsByDistrictId(districtId);
        } else if (address != null && address.getDistrict() != null) {
            wards = wardService.getWardsByDistrictId(address.getDistrict().getId());
        }
        // **Luôn truyền danh sách (kể cả rỗng) để Thymeleaf không lỗi**
        model.addAttribute("wards", wards);

        return "profile/update-shipping-address";
    }


    @PostMapping("/update-address")
    public String saveAddress(Authentication authentication,
                              @ModelAttribute UserAddress address,
                              @RequestParam("districtId") Long districtId,
                              @RequestParam("wardId") Long wardId,
                              @RequestParam(value = "isDefault", required = false) Boolean isDefault,
                              RedirectAttributes redirectAttributes) {
        User user = userService.getAuthenticatedUser(authentication);

        address.setDistrict(districtService.getById(districtId));
        address.setWard(wardService.getWardById(wardId));

        boolean isDefaultFlag = Boolean.TRUE.equals(address.getIsDefault()) || Boolean.TRUE.equals(isDefault);

        try {
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

package com.thungcam.chacalang.controller.shipper;

import com.thungcam.chacalang.dto.ShipperOrderAvailableDTO;
import com.thungcam.chacalang.entity.District;
import com.thungcam.chacalang.entity.Ward;
import com.thungcam.chacalang.service.DistrictService;
import com.thungcam.chacalang.service.ShippingOrderAvailableService;
import com.thungcam.chacalang.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperOrderAvailableController {

    private final ShippingOrderAvailableService shippingOrderAvailableService;
    private final DistrictService districtService;
    private final WardService wardService;

    @GetMapping("/orders-available")
    public String listOrdersAvailable(
            @RequestParam("branchId") Long branchId,
            @RequestParam(value = "districtId", required = false) Long districtId,
            @RequestParam(value = "wardId", required = false) Long wardId,
            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(value = "toDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            Model model
    ) {
        List<District> districts = districtService.getDistrictsByBranch(branchId);
        List<Ward> wards = districtId != null ? wardService.getWardsByDistrict(districtId) : List.of();

        String districtName = (districtId != null) ?
                districts.stream().filter(d -> d.getId().equals(districtId)).map(District::getName).findFirst().orElse(null) : null;
        String wardName = (wardId != null) ?
                wards.stream().filter(w -> w.getId().equals(wardId)).map(Ward::getName).findFirst().orElse(null) : null;

        List<ShipperOrderAvailableDTO> orders = shippingOrderAvailableService.getOrdersAvailable(
                branchId, districtName, wardName, fromDate, toDate
        );

        model.addAttribute("orders", orders);
        model.addAttribute("branchId", branchId);
        model.addAttribute("districts", districts);
        model.addAttribute("wards", wards);
        model.addAttribute("districtId", districtId);
        model.addAttribute("wardId", wardId);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("activePage", "orders-available");
        return "shipper/shipper-orders-available";
    }

}


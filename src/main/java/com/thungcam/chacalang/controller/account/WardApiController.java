package com.thungcam.chacalang.controller.account;

import com.thungcam.chacalang.dto.WardSimpleDTO;
import com.thungcam.chacalang.entity.Ward;
import com.thungcam.chacalang.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-address")
@RequiredArgsConstructor
public class WardApiController {

    private final WardService wardService;

    @GetMapping("/wards")
    @ResponseBody
    public List<WardSimpleDTO> getWardsByDistrict(@RequestParam Long districtId) {
//        System.out.println("Fetching wards for district ID: " + districtId);
//        System.out.println("Available wards: " + wardService.getWardsByDistrictId(districtId));
        return wardService.getWardsByDistrictId(districtId)
                .stream()
                .map(w -> new WardSimpleDTO(w.getId(), w.getName()))
                .collect(Collectors.toList());
    }
}


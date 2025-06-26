//package com.thungcam.chacalang.controller.account;
//
//import com.thungcam.chacalang.entity.District;
//import com.thungcam.chacalang.entity.Ward;
//import com.thungcam.chacalang.service.DistrictService;
//import com.thungcam.chacalang.service.WardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/location")
//@RequiredArgsConstructor
//public class LocationController {
//
//    private final DistrictService districtService;
//    private final WardService wardService;
//
//    @GetMapping("/districts/ha-noi")
//    public ResponseEntity<List<District>> getAllDistricts() {
//        return ResponseEntity.ok(districtService.getAllDistricts());
//    }
//
//    @GetMapping("/wards-by-id")
//    public ResponseEntity<List<Ward>> getWardsByDistrictId(@RequestParam Long districtId) {
//        return ResponseEntity.ok(wardService.getWardsByDistrictId(districtId));
//    }
//}

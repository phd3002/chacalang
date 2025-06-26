//package com.thungcam.chacalang.controller;
//
//import location.WardData;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/location")
//public class CustomLocationController {
//
//    @GetMapping("/districts/ha-noi")
//    public List<Map<String, Object>> getDistricts() {
//        return WardData.getWardMap().keySet().stream()
//                .map(name -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("name", name);
//                    map.put("code", name.hashCode());
//                    return map;
//                })
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/wards")
//    public List<Map<String, Object>> getWards(@RequestParam String districtName) {
//        List<String> wards = WardData.getWardsByDistrict(districtName);
//        return wards.stream()
//                .map(name -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("name", name);
//                    return map;
//                })
//                .collect(Collectors.toList());
//    }
//}

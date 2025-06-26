package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Ward;

import java.util.List;

public interface WardService {
    List<Ward> getWardsByDistrictName(String districtName);

    List<Ward> getWardsByDistrictId(Long districtId);

    Ward getWardById(Long id);
}


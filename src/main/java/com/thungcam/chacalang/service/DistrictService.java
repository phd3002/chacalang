package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.District;

import java.util.List;

public interface DistrictService {
    List<District> getAllDistricts();

    District getById(Long id);

    List<District> getDistrictsByBranch(Long branchId);

    String getDistrictNameById(Long id);
}

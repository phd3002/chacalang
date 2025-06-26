package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Ward;
import com.thungcam.chacalang.repository.WardRepository;
import com.thungcam.chacalang.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WardServiceImp implements WardService {
    private final WardRepository wardRepository;

    @Override
    public List<Ward> getWardsByDistrictName(String districtName) {
        return wardRepository.findByDistrict_Name(districtName);
    }

    @Override
    public List<Ward> getWardsByDistrictId(Long districtId) {
        return wardRepository.findByDistrict_Id(districtId);
    }

    @Override
    public Ward getWardById(Long id) {
        return wardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ward not found with id: " + id));
    }
}

package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.District;
import com.thungcam.chacalang.repository.DistrictRepository;
import com.thungcam.chacalang.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;

    @Override
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    @Override
    public District getById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("District not found with id: " + id));
    }

    @Override
    public List<District> getDistrictsByBranch(Long branchId) {
        return districtRepository.findByBranch_Id(branchId);
    }

    @Override
    public String getDistrictNameById(Long id) {
        return districtRepository.findById(id)
                .map(District::getName)
                .orElseThrow(() -> new IllegalArgumentException("District not found with id: " + id));
    }
}

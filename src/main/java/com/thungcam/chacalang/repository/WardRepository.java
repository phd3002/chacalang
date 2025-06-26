package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    List<Ward> findByDistrict_Name(String districtName);

    List<Ward> findByNameContainingIgnoreCase(String name);

    List<Ward> findByDistrict_Id(Long districtId);
}
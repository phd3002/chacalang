package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("SELECT d FROM BranchDistrict bd JOIN bd.district d WHERE bd.branch.id = :branchId")
    List<District> findByBranch_Id(Long branchId);
}

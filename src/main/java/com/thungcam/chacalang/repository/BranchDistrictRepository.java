package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.BranchDistrict;
import com.thungcam.chacalang.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchDistrictRepository extends JpaRepository<BranchDistrict, Long> {
    BranchDistrict findFirstByDistrict(District district);

}

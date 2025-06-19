package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.BranchMenuStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchMenuStockRepository extends JpaRepository<BranchMenuStock, Long> {
    List<BranchMenuStock> findByBranch_IdAndQuantity(int branchId, int quantity); // dùng nếu muốn tùy

    List<BranchMenuStock> findByBranch_IdAndQuantityLessThanEqual(Long branchId, int quantity);
}


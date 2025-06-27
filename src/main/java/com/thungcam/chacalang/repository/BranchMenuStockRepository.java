package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.BranchMenuStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchMenuStockRepository extends JpaRepository<BranchMenuStock, Long> {
    @Query("SELECT s FROM BranchMenuStock s JOIN FETCH s.menu WHERE s.branch.id = :branchId")
    List<BranchMenuStock> findByBranchIdWithMenu(@Param("branchId") Long branchId);

    List<BranchMenuStock> findByBranch_IdAndQuantityLessThanEqual(Long branchId, int quantity);

    @Query("SELECT bms FROM BranchMenuStock bms WHERE bms.branch.id = :branchId AND bms.quantity <= :minStock")
    List<BranchMenuStock> findLowStockByBranch(@Param("branchId") Long branchId, @Param("minStock") Integer minStock);
}


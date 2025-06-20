package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.BranchMenuStock;

import java.util.List;

public interface BranchMenuStockService {
    List<BranchMenuStock> getOutOfStockMenuByBranch(Long branchId);

    List<BranchMenuStock> getStockByBranch(Long branchId);

    void updateQuantity(Long stockId, int quantity);
}

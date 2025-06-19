package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.BranchMenuStock;

import java.util.List;

public interface BranchMenuStockService {
    List<BranchMenuStock> getOutOfStockMenuByBranch(Long branchId);
}

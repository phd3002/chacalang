package com.thungcam.chacalang.service;

import com.thungcam.chacalang.dto.BranchDashboardDTO;

public interface BranchDashboardService {
    BranchDashboardDTO getDashboardData(Long branchId);
}

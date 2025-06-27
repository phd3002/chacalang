package com.thungcam.chacalang.service;

import com.thungcam.chacalang.dto.StaffDashboardDTO;

public interface StaffDashboardService {
    StaffDashboardDTO getDashboardData(Long branchId);
}

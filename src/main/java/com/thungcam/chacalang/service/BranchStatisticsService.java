package com.thungcam.chacalang.service;

import java.time.LocalDateTime;
import java.util.Map;

public interface BranchStatisticsService {
    Map<String, Object> getBranchStatistics(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);
}

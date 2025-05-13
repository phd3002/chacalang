package com.thungcam.chacalang.service;

import java.math.BigDecimal;
import java.util.Map;

public interface AdminDashboardService {
    long getTotalOrders();
    BigDecimal getTotalRevenue();
    long getNewCustomers();
    Map<String, BigDecimal> getRevenueChartData();
}


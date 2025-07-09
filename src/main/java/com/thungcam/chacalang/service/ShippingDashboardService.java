package com.thungcam.chacalang.service;

import com.thungcam.chacalang.dto.ShipperDashboardDTO;

public interface ShippingDashboardService {
    ShipperDashboardDTO getShipperDashboard(Long shipperId, Long branchId);
}

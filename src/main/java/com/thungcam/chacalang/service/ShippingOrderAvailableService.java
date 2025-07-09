package com.thungcam.chacalang.service;

import com.thungcam.chacalang.dto.ShipperOrderAvailableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ShippingOrderAvailableService {
    List<ShipperOrderAvailableDTO> getOrdersAvailable(Long branchId, String district, String ward, LocalDateTime fromDate, LocalDateTime toDate);

}

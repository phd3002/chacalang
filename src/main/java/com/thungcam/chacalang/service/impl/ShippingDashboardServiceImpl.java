// ShippingDashboardServiceImpl.java
package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.dto.ShipperDashboardDTO;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.Branch;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.repository.OrderShipperRepository;
import com.thungcam.chacalang.repository.UserRepository;
import com.thungcam.chacalang.repository.BranchRepository;
import com.thungcam.chacalang.service.ShippingDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingDashboardServiceImpl implements ShippingDashboardService {

    private final OrderShipperRepository orderShipperRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    @Override
    public ShipperDashboardDTO getShipperDashboard(Long shipperId, Long branchId) {
        long pendingOrders = orderShipperRepository.countOrdersShipByBranchAndStatus(
                branchId, OrderStatus.PENDING
        );
        long shippingOrders = orderShipperRepository.countOrdersByBranchAndStatusAndShipper(
                branchId, OrderStatus.SHIPPING, shipperId
        );
        long deliveredOrders = orderShipperRepository.countOrdersByBranchAndStatusAndShipper(
                branchId, OrderStatus.DELIVERED, shipperId
        );
        long completedOrders = orderShipperRepository.countOrdersByBranchAndStatusAndShipper(
                branchId, OrderStatus.COMPLETED, shipperId
        );
        long cancelledOrders = orderShipperRepository.countOrdersByBranchAndStatusAndShipper(
                branchId, OrderStatus.CANCELLED, shipperId
        );

        User shipper = userRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        return new ShipperDashboardDTO(
                pendingOrders,
                shippingOrders,
                deliveredOrders,
                completedOrders,
                cancelledOrders,
                shipper.getFullName(),
                branch.getName(),
                shipper.getPhone(),
                shipper.getEmail()
        );
    }
}

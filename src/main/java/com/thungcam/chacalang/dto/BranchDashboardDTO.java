package com.thungcam.chacalang.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDashboardDTO {
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private Long todayReservations;
    private Long pendingOrders;
    private Long completedOrders;
    private Long cancelledOrders;
    private Long confirmedOrders;
    private Long preparingOrders;
    private Long shippingOrders;
    private Long refundedOrders;

}


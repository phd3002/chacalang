package com.thungcam.chacalang.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipperDashboardDTO {
    private long pendingOrders;    // Đơn chờ nhận (PREPARING, CONFIRMED)
    private long shippingOrders;   // Đơn đang giao (SHIPPING)
    private long deliveredOrders; // Đơn đã giao (DELIVERED)
    private long completedOrders;  // Đơn đã hoàn tất (COMPLETED)
    private long cancelledOrders;  // Đơn hủy (FAILED)
    private String shipperName;
    private String branchName;
    private String phone;
    private String email;
}

package com.thungcam.chacalang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDashboardDTO {
    // Thống kê đơn hàng
    private long ordersNew;
    private long ordersPreparing;
    private long ordersShipping;
    private long ordersCompleted;
    private long ordersCancelled;
    private BigDecimal todayRevenue;

    // Cảnh báo tồn kho
    private List<StockAlertItem> stockAlerts;

    // Đặt bàn hôm nay
    private List<ReservationTodayItem> todayReservations;

    // Thông báo
    private List<NotificationItem> notifications;

    // -------- Inner static class cho hiển thị dashboard --------
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StockAlertItem {
        private String menuName;
        private int quantity;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationTodayItem {
        private String customerName;
        private LocalTime reservationTime;
        private int numberOfPeople;
        private String status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NotificationItem {
        private String title;
        private String message;
        private String type; // urgent, warning, info
        private LocalDateTime createdAt;
    }
}

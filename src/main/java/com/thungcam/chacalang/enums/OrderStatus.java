package com.thungcam.chacalang.enums;

public enum OrderStatus {
    PENDING,        // Đơn hàng mới tạo, chưa xử lý
    CONFIRMED,      // Đã xác nhận bởi nhân viên
    PREPARING,      // Đang chuẩn bị món
    SHIPPING,       // Đang giao hàng (nếu có)
    COMPLETED,      // Giao hàng thành công / khách nhận tại quán
    CANCELLED,       // Khách huỷ hoặc hết món
    REFUNDED;

    public static OrderStatus fromString(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name().equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Unknown order status: " + status);
    }
}

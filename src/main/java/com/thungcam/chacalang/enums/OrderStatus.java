package com.thungcam.chacalang.enums;

import lombok.Getter;


@Getter
public enum OrderStatus {
    PENDING("Đang Xử Lý"),
    CONFIRMED("Đã Xác Nhận"),
    PREPARING("Đang Chuẩn Bị"),
    ASSIGNED("Đã Giao Nhân Viên"),
    SHIPPING("Đang Giao Hàng"),

    WAITING_FOR_PICKUP("Chờ Nhận Tại Quán"),
    DELIVERED("Đã Giao Hàng"),
    COMPLETED("Hoàn Tất"),      // Giao hàng thành công / khách nhận tại quán
    CANCELLED("Đã Huỷ"),
    FAILED("Giao Hàng Thất Bại"),
    REFUNDED("Hoàn Tiền");
    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }
}

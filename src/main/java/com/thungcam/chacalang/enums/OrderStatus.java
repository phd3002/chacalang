package com.thungcam.chacalang.enums;

import lombok.Getter;


@Getter
public enum OrderStatus {
    PENDING("Đang Xử Lý"),        // Đơn hàng mới tạo, chưa xử lý
    CONFIRMED("Đã Xác Nhận"),      // Đã xác nhận bởi nhân viên
    PREPARING("Đang Chuẩn Bị"),      // Đang chuẩn bị món
    ASSIGNED("Đã Giao Nhân Viên"), // Đã giao cho nhân viên giao hàng (nếu có)
    SHIPPING("Đang Giao Hàng"),       // Đang giao hàng (nếu có)

    WAITING_FOR_PICKUP("Chờ Nhận Tại Quán"), // Chờ khách đến nhận tại quán
    DELIVERED("Đã Giao Hàng"),      // Đã giao hàng (nếu có)
    COMPLETED("Hoàn Tất"),      // Giao hàng thành công / khách nhận tại quán
    CANCELLED("Đã Huỷ"),       // Khách huỷ hoặc hết món
    FAILED("Giao Hàng Thất Bại"), // Giao hàng thất bại (nếu có)
    REFUNDED("Hoàn Tiền");       // Đã hoàn tiền cho khách

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }
}

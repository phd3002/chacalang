package com.thungcam.chacalang.enums;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    PENDING("Đang chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    CANCELLED("Đã hủy");

    private final String label;

    ReservationStatus(String label) {
        this.label = label;
    }
}

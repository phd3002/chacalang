package com.thungcam.chacalang.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("Đang chờ thanh toán"),
    PAID("Đã thanh toán"),
    CANCELED("Đã hủy"),
    REFUNDED("Đã hoàn tiền");

    private final String label;

    PaymentStatus(String label) {
        this.label = label;
    }
}

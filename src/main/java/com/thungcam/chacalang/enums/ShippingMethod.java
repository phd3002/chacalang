package com.thungcam.chacalang.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ShippingMethod {
    DELIVERY("Giao hàng tận nơi"),
    PICKUP("Hẹn lấy tại cửa hàng");

    private final String label;

    ShippingMethod(String label) {
        this.label = label;
    }

}


package com.thungcam.chacalang.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("Hoạt động"),
    INACTIVE("Không hoạt động"),
    BANNED("Bị cấm");

    private final String label;

    UserStatus(String label) {
        this.label = label;
    }
}

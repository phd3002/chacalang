package com.thungcam.chacalang.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OrderCheckoutDTO {
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private String deliveryTime;
    private String note;
    private Map<Long, Integer> cartData; // menuId -> quantity
} 
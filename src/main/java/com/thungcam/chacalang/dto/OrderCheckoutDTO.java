package com.thungcam.chacalang.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class OrderCheckoutDTO {
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private String city;
    private String district;
    private String ward;
    private String shippingMethod; // "delivery" | "pickup"
    private String pickupCity;
    private String pickupStore;
    private String note;
//    private Long paymentMethodId;
    private Long addressId;        // dùng khi delivery
    private Long branchId;         // dùng khi pickup
    private Long paymentMethod;    // bắt buộc
}

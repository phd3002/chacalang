package com.thungcam.chacalang.dto;

import com.thungcam.chacalang.enums.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ShipperOrderAvailableDTO {
    private Long orderId;
    private String orderCode;
    private String customerName;
    private String customerPhone;
    private String address;
    private String district;
    private String ward;
    private LocalDateTime createdAt;
    private OrderStatus status;
}

package com.thungcam.chacalang.dto;

import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.enums.ShippingMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderViewDTO {
    private Long id;
    private String orderCode;
    private String customerName;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private ShippingMethod shippingMethod;

    public String getStatusColor() {
        return switch (status) {
            case PENDING -> "badge-warning";
            case CONFIRMED, PREPARING, SHIPPING -> "badge-info";
            case COMPLETED -> "badge-success";
            case CANCELLED -> "badge-danger";
            default -> "badge-secondary";
        };
    }
}

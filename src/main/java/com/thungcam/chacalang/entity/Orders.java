package com.thungcam.chacalang.entity;

import com.thungcam.chacalang.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "orders", schema = "thungcam_db")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Size(max = 20)
    @NotNull
    @Column(name = "customer_phone", nullable = false, length = 20)
    private String customerPhone;

    @Size(max = 100)
    @Column(name = "customer_email", length = 100)
    private String customerEmail;

    @NotNull
    @Lob
    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Lob
    @Column(name = "note")
    private String note;

//    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "shipping_fee", precision = 10, scale = 2)
    private BigDecimal shippingFee;

    @Lob
    @Column(name = "shipping_info")
    private String shippingInfo;

    @Column(name = "payment_method_id")
    private Long paymentMethodId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "order_code", unique = true, nullable = false)
    private String orderCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

}
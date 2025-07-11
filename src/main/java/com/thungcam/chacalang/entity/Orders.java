package com.thungcam.chacalang.entity;

import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.enums.ShippingMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "orders", schema = "thungcam_db")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Invoice invoice;

    @Size(max = 100)
    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Size(max = 20)
    @Column(name = "customer_phone", nullable = false, length = 20)
    private String customerPhone;

    @Size(max = 100)
    @Column(name = "customer_email", length = 100)
    private String customerEmail;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "note")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", nullable = false)
    private ShippingMethod shippingMethod;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @OneToOne(mappedBy = "order")
    private Review review;

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
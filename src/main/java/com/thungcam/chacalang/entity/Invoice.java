package com.thungcam.chacalang.entity;

import com.thungcam.chacalang.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "invoice", schema = "thungcam_db", indexes = {
        @Index(name = "order_id", columnList = "order_id"),
        @Index(name = "payment_method_id", columnList = "payment_method_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "invoice_code", columnNames = {"invoice_code"})
})
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    private com.thungcam.chacalang.entity.Orders order;

    @Size(max = 100)
    @NotNull
    @Column(name = "invoice_code", nullable = false, length = 100)
    private String invoiceCode;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "issued_date")
    private LocalDateTime issuedDate;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

//    @ColumnDefault("0.00")
    @Column(name = "shipping_fee", precision = 10, scale = 2)
    private BigDecimal shippingFee;

//    @ColumnDefault("0.00")
    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @ColumnDefault("0.00")
    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @NotNull
    @Column(name = "final_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal finalAmount;

//    @ColumnDefault("'UNPAID'")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private com.thungcam.chacalang.entity.PaymentMethod paymentMethod;

    @Lob
    @Column(name = "notes")
    private String notes;
}
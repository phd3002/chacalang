package com.thungcam.chacalang.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
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
    private com.thungcam.chacalang.entity.Order order;

    @Size(max = 100)
    @NotNull
    @Column(name = "invoice_code", nullable = false, length = 100)
    private String invoiceCode;

//    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "issued_date")
    private Instant issuedDate;

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
    @Lob
    @Column(name = "payment_status")
    private String paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private com.thungcam.chacalang.entity.PaymentMethod paymentMethod;

    @Lob
    @Column(name = "notes")
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.thungcam.chacalang.entity.Order getOrder() {
        return order;
    }

    public void setOrder(com.thungcam.chacalang.entity.Order order) {
        this.order = order;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public Instant getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Instant issuedDate) {
        this.issuedDate = issuedDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public com.thungcam.chacalang.entity.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(com.thungcam.chacalang.entity.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
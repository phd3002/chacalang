package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.paymentStatus = com.thungcam.chacalang.enums.PaymentStatus.PAID")
    BigDecimal sumTotalRevenue();

    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.paymentStatus = com.thungcam.chacalang.enums.PaymentStatus.PAID")
    long countInvoices();

    @Query("SELECT COUNT(DISTINCT i.order.user) FROM Invoice i WHERE i.order.user.createdAt >= :startOfMonth AND i.paymentStatus = com.thungcam.chacalang.enums.PaymentStatus.PAID")
    long countNewCustomersThisMonth(@Param("startOfMonth") LocalDateTime startOfMonth);

    @Query("SELECT DATE(i.issuedDate), SUM(i.totalAmount) FROM Invoice i WHERE i.issuedDate >= :fromDate AND i.paymentStatus = com.thungcam.chacalang.enums.PaymentStatus.PAID GROUP BY DATE(i.issuedDate) ORDER BY DATE(i.issuedDate)")
    List<Object[]> getDailyRevenue(@Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT COALESCE(SUM(i.finalAmount), 0) FROM Invoice i WHERE i.order.branch.id = :branchId and i.order.status = com.thungcam.chacalang.enums.OrderStatus.COMPLETED")
    BigDecimal sumFinalAmountByBranch(@Param("branchId") Long branchId);

    Optional<Invoice> findByOrderId(Long orderId);
}


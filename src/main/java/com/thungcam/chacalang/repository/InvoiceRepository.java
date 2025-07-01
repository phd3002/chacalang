package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Invoice;
import com.thungcam.chacalang.enums.PaymentStatus;
import com.thungcam.chacalang.enums.ShippingMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

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

    @Query("SELECT COALESCE(SUM(i.finalAmount), 0) FROM Invoice i WHERE DATE(i.issuedDate) = CURRENT_DATE")
    double getTodayRevenue();

    @Query("SELECT SUM(i.finalAmount) FROM Invoice i " +
            "WHERE i.paymentStatus = :status " +
            "AND i.issuedDate BETWEEN :start AND :end " +
            "AND i.order.branch.id = :branchId")
    BigDecimal sumFinalAmountByStatusAndBranchAndDate(
            @Param("status") PaymentStatus paymentStatus,
            @Param("branchId") Long branchId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT i FROM Invoice i " +
            "JOIN i.order o " +
            "WHERE o.branch.id = :branchId AND o.shippingMethod = 'PICKUP' " +
            "AND (:search IS NULL OR i.invoiceCode LIKE %:search% OR o.customerName LIKE %:search%) " +
            "AND (:paymentStatus IS NULL OR i.paymentStatus = :paymentStatus) " +
            "AND (:paymentMethodId IS NULL OR i.paymentMethod.id = :paymentMethodId) " +
            "AND (COALESCE(:dateFrom, NULL) IS NULL OR i.issuedDate >= :dateFrom) " +
            "AND (COALESCE(:dateTo, NULL) IS NULL OR i.issuedDate <= :dateTo)")
    Page<Invoice> searchInvoices(
            @Param("branchId") Long branchId,
            @Param("search") String search,
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("paymentMethodId") Long paymentMethodId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            Pageable pageable
    );

    List<Invoice> findByOrderBranchIdAndOrderShippingMethod(Long branchId, ShippingMethod shippingMethod);
}


package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.OrderStatus;
import com.thungcam.chacalang.enums.ShippingMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Optional<Orders> findByIdAndUser(Long id, User user);

    Long countByBranch_Id(Long branchId);

    Long countByBranch_IdAndStatus(Long branch_id, OrderStatus status);

    List<Orders> findAllByBranch_IdOrderByCreatedAtDesc(Long branchId);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.branch.id = :branchId AND o.createdAt >= :fromDate AND o.createdAt <= :toDate")
    long countByBranch(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);

    @Query("SELECT SUM(i.finalAmount) FROM Invoice i WHERE i.order.branch.id = :branchId AND i.order.status = 'COMPLETED' AND i.issuedDate >= :fromDate AND i.issuedDate <= :toDate")
    Double sumRevenueByBranch(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.branch.id = :branchId AND o.status = 'CANCELLED' AND o.createdAt >= :fromDate AND o.createdAt <= :toDate")
    long countCancelledByBranch(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);

    @Query("SELECT DATE(o.createdAt), COUNT(o) FROM Orders o WHERE o.branch.id = :branchId AND o.createdAt >= :fromDate AND o.createdAt <= :toDate GROUP BY DATE(o.createdAt) ORDER BY DATE(o.createdAt)")
    List<Object[]> countOrdersByDay(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);

    @Query("SELECT DATE(i.issuedDate), SUM(i.finalAmount) FROM Invoice i WHERE i.order.branch.id = :branchId AND i.order.status = 'COMPLETED' AND i.issuedDate >= :fromDate AND i.issuedDate <= :toDate GROUP BY DATE(i.issuedDate) ORDER BY DATE(i.issuedDate)")
    List<Object[]> sumRevenueByDay(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);

    long countByStatusAndBranchId(OrderStatus status, Long branch_id);

    // Lấy tất cả đơn pickup của 1 chi nhánh
    List<Orders> findAllByShippingMethodAndBranch_IdOrderByCreatedAtDesc(ShippingMethod shippingMethod, Long branchId);

    // Lấy đơn pickup có filter (JPQL)
    @Query("SELECT o FROM Orders o " +
            "WHERE o.shippingMethod = :shippingMethod " +
            "AND o.branch.id = :branchId " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:search IS NULL OR :search = '' OR LOWER(o.orderCode) LIKE %:search% OR LOWER(o.customerName) LIKE %:search%) " +
            "AND (:dateFrom IS NULL OR o.createdAt >= :dateFrom) " +
            "AND (:dateTo IS NULL OR o.createdAt <= :dateTo) " +
            "ORDER BY o.createdAt DESC")
    List<Orders> searchPickupOrdersByBranch(
            ShippingMethod shippingMethod,
            Long branchId,
            OrderStatus status,
            String search,
            LocalDateTime dateFrom,
            LocalDateTime dateTo
    );

    long countByShippingMethodAndStatusAndBranch_Id(ShippingMethod shippingMethod, OrderStatus status, Long branchId);

    @Query("""
                SELECT o FROM Orders o
                WHERE o.branch.id = :branchId
                  AND o.status IN :statuses
                  AND o.shippingMethod = 'DELIVERY'
                  AND NOT EXISTS (
                      SELECT 1 FROM OrderShipper os WHERE os.order.id = o.id
                  )
            """)
    List<Orders> findOrdersAvailableForShipper(
            Long branchId,
            List<OrderStatus> statuses,
            String district,
            String ward,
            LocalDateTime fromDate,
            LocalDateTime toDate
    );

    @Query("SELECT o.id FROM Orders o WHERE o.status IN :statuses")
    List<Long> findOrderIdsByStatusIn(@Param("statuses") List<OrderStatus> statuses);


}
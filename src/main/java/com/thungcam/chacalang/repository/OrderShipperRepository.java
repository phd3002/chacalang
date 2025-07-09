// OrderShipperRepository.java
package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.OrderShipper;
import com.thungcam.chacalang.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderShipperRepository extends JpaRepository<OrderShipper, Long> {

    @Query("SELECT COUNT(o) FROM Orders o " +
            "WHERE o.branch.id = :branchId AND o.status = :status AND o.shippingMethod = 'DELIVERY'")
    long countOrdersShipByBranchAndStatus(Long branchId, OrderStatus status);


    @Query("SELECT COUNT(os) FROM OrderShipper os " +
            "JOIN os.order o " +
            "WHERE o.branch.id = :branchId AND o.status = :status AND os.shipper.id = :shipperId")
    long countOrdersByBranchAndStatusAndShipper(Long branchId, OrderStatus status, Long shipperId);

    boolean existsByOrder_Id(Long orderId);

    @Query("""
        SELECT os FROM OrderShipper os
        JOIN FETCH os.order o
        WHERE os.shipper.id = :shipperId
    """)
    List<OrderShipper> findAssignedShippingOrdersByShipper(Long shipperId);

    Optional<OrderShipper> findByOrder_IdAndShipper_Id(Long orderId, Long shipperId);

    @Query("""
        SELECT os FROM OrderShipper os
        WHERE os.deliveredAt <= :threshold
        AND os.order.status = 'DELIVERED'
    """)
    List<OrderShipper> findDeliveredOrdersBefore(LocalDateTime threshold);

    @Query("""
        SELECT os FROM OrderShipper os
        JOIN FETCH os.order o
        WHERE os.shipper.id = :shipperId
          AND o.status = :status
          AND (:fromDate IS NULL OR os.deliveredAt >= :fromDate)
          AND (:toDate IS NULL OR os.deliveredAt <= :toDate)
    """)
    List<OrderShipper> findHistoryOrders(
            Long shipperId,
            OrderStatus status,
            LocalDateTime fromDate,
            LocalDateTime toDate
    );

}

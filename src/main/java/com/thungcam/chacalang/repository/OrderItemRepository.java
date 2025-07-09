package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.OrderItem;
import com.thungcam.chacalang.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.menu WHERE oi.order.id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.menu WHERE oi.order = :order")
    List<OrderItem> findByOrderWithMenu(@Param("order") Orders order);

    @Query("SELECT oi.menu.name, SUM(oi.quantity) FROM OrderItem oi WHERE oi.order.branch.id = :branchId AND oi.order.createdAt >= :fromDate AND oi.order.createdAt <= :toDate GROUP BY oi.menu.id, oi.menu.name ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findTopMenusByBranch(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);

    List<OrderItem> findByOrder_Id(Long orderId);
} 
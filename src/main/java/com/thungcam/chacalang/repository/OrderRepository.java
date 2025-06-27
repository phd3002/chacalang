package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserOrderByCreatedAtDesc(User user);

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

    @Query("SELECT oi.menu.name, SUM(oi.quantity) AS total FROM OrderItem oi WHERE oi.order.branch.id = :branchId AND oi.order.createdAt >= :fromDate AND oi.order.createdAt <= :toDate GROUP BY oi.menu.id, oi.menu.name ORDER BY total DESC")
    List<Object[]> findTopMenusByBranch(Long branchId, LocalDateTime fromDate, LocalDateTime toDate);

    long countByStatusAndBranchId(OrderStatus status, Long branch_id);

    long countByStatusInAndBranchId(Collection<OrderStatus> status, Long branch_id);

}
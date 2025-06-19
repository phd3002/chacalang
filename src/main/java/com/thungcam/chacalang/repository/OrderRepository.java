package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Orders;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserOrderByCreatedAtDesc(User user);

    Optional<Orders> findByIdAndUser(Long id, User user);


//    Optional<Orders> findById(Long id);

    Long countByBranch_Id(Long branchId);

    Long countByBranch_IdAndStatus(Long branch_id, OrderStatus status);

    List<Orders> findAllByBranch_IdOrderByCreatedAtDesc(Long branchId);
}
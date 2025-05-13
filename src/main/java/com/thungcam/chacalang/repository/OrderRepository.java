package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("select sum(o.totalPrice) from Orders o")
    BigDecimal getTotalRevenue();

    Optional<Orders> findByOrderCode(String orderCode);
}

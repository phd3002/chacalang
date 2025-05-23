package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_Id(Long userId);

    Optional<Cart> findBySessionId(String sessionId);
}

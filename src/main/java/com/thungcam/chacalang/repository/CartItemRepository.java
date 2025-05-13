package com.thungcam.chacalang.repository;

import com.thungcam.chacalang.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCart_IdAndMenu_Id(Long cartId, Long menuId);

    List<CartItem> findAllByCart_Id(Long cartId);

    void deleteByCart_IdAndMenu_Id(Long cartId, Long menuId);
}

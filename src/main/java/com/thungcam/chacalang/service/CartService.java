package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Cart;
import com.thungcam.chacalang.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    Cart getCart(Long userId);

    void addToCart(Long userId, Long menuId, int quantity);

    void updateQuantity(Long userId, Long menuId, int quantity);

    void removeFromCart(Long userId, Long menuId);

    List<CartItem> getCartItems(Long userId);

    BigDecimal calculateTotal(Long userId);

    void clearCart(Long userId);

//    void updateCartStatus(Cart cart);
}

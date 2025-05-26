package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Cart;
import com.thungcam.chacalang.entity.CartItem;
import com.thungcam.chacalang.entity.Menu;
import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.repository.CartItemRepository;
import com.thungcam.chacalang.repository.CartRepository;
import com.thungcam.chacalang.repository.MenuRepository;
import com.thungcam.chacalang.repository.UserRepository;
import com.thungcam.chacalang.service.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final MenuRepository menuRepository;

    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, MenuRepository menuRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart getCart(Long userId) {
        return cartRepository.findByUser_Id(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId)));
                    cart.setCreatedAt(LocalDateTime.now());
                    return cartRepository.save(cart);
                });
    }

    @Override
    public void addToCart(Long userId, Long menuId, int quantity) {
        Cart cart = getCart(userId);
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found: " + menuId));
        CartItem cartItem = cartItemRepository.findByCart_IdAndMenu_Id(cart.getId(), menuId)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setMenu(menu);
                    newItem.setPrice(menu.getPrice());
                    newItem.setQuantity(0);
                    return newItem;
                });
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setAddedAt(LocalDateTime.now());
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updateQuantity(Long userId, Long menuId, int quantity) {
        Cart cart = getCart(userId);
        CartItem cartItem = cartItemRepository.findByCart_IdAndMenu_Id(cart.getId(), menuId)
                .orElseThrow(() -> new RuntimeException("Cart item not found: " + menuId));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

    }

    @Override
    public void removeFromCart(Long userId, Long menuId) {
        Cart cart = getCart(userId);
        cartItemRepository.deleteByCart_IdAndMenu_Id(cart.getId(), menuId);
    }

    @Override
    public List<CartItem> getCartItems(Long userId) {
        Cart cart = getCart(userId);
        return cartItemRepository.findAllByCart_Id(cart.getId());
    }

    @Override
    public BigDecimal calculateTotal(Long userId) {
        return getCartItems(userId).stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        cartItemRepository.deleteAllByCart_Id(cart.getId());
    }
}

package com.thungcam.chacalang.dto;

import com.thungcam.chacalang.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long menuId;
    private String name;
    private int quantity;
    private BigDecimal price;

    public CartItemDTO(CartItem item) {
        this.menuId = item.getMenu().getId();
        this.name = item.getMenu().getName();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
    }
}

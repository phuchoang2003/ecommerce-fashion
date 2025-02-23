package org.example.ecommercefashion.module.cart.dto;

import lombok.Builder;
import org.example.ecommercefashion.module.cart.entity.Cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Builder
public record CartResponse(Long cartId,
                           Long userId,
                           BigDecimal totalAmount,
                           Integer totalItems,
                           Set<CartItemResponse> cartItems) implements Serializable {
    public static CartResponse fromEntity(Cart cart, Set<CartItemResponse> cartResponses, Long userId) {
        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(userId)
                .totalAmount(cart.getTotalAmount())
                .totalItems(cart.getCartItems().size())
                .cartItems(cartResponses)
                .build();
    }
}

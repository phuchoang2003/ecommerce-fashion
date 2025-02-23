package org.example.ecommercefashion.module.cart.dto;


import lombok.Builder;
import org.example.ecommercefashion.module.cart.entity.CartItem;
import org.example.ecommercefashion.module.product.entity.Variant;
import org.example.ecommercefashion.module.product.enums.ProductState;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
public record CartItemResponse(Long cartItemId,
                               Long productId,
                               Long productVariantId,
                               String productName,
                               String categoryName,
                               BigDecimal priceItem,
                               ProductState state,
                               String urlImage,
                               Integer quantity,
                               BigDecimal totalPrice,
                               Set<Variant> attributes
) implements Serializable {
    public static CartItemResponse fromEntity(CartItem cartItem, String urlImage) {
        Set<Variant> attributes = new HashSet<>();
        if (cartItem.getProductVariant() != null) {
            attributes.addAll(cartItem.getProductVariant().getVariants());
        }

        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProductId())
                .productVariantId(cartItem.getProductVariantId())
                .productName(cartItem.getProduct().getName())
                .categoryName(cartItem.getProduct().getCategory().getName())
                .priceItem(cartItem.getProduct().getPrice())
                .state(cartItem.getProduct().getState())
                .urlImage(urlImage)
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getPrice())
                .attributes(attributes)
                .build();
    }
}

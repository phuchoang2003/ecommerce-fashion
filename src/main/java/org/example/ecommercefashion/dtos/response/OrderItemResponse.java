package org.example.ecommercefashion.dtos.response;

import lombok.Builder;
import org.example.ecommercefashion.entities.postgres.OrderItem;
import org.example.ecommercefashion.entities.postgres.Variant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
public record OrderItemResponse(
        Long orderItemId,
        Long productId,
        Long productVariantId,
        String productName,
        Set<Variant> attributes,
        String productImage,
        BigDecimal price,
        Integer quantity,
        BigDecimal total) {

    public static OrderItemResponse fromEntity(OrderItem item, String urlImage) {
        Set<Variant> attributes = new HashSet<>();
        if (item.getProductVariant() != null) {
            attributes.addAll(item.getProductVariant().getVariants());
        }

        return OrderItemResponse.builder()
                .orderItemId(item.getId())
                .productId(item.getProductId())
                .productVariantId(item.getProductVariantId())
                .productName(item.getProduct().getName())
                .attributes(attributes)
                .productImage(urlImage)
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .total(item.getTotal())
                .build();
    }

}

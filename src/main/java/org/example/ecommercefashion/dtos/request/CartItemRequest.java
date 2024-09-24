package org.example.ecommercefashion.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.annotations.EnumPattern;
import org.example.ecommercefashion.enums.ProductType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CartItemRequest {
    @NotNull(message = "Product item id is required")
    @Positive(message = "Product item id must be greater than 0")
    private Long productItemId;

    @EnumPattern(regexp = "PRODUCT|PRODUCT_VARIANT", name = "type")
    @NotNull(message = "Type is required")
    private ProductType type;

    @PositiveOrZero(message = "Quantity must be greater or equal than 0")
    @NotNull(message = "Quantity is required")
    private Integer quantity;
}

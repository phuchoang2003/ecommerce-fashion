package org.example.ecommercefashion.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.entities.postgres.Variant;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateCartItemRequest {
    @PositiveOrZero(message = "Quantity must be greater or equal than 0")
    @NotNull(message = "Quantity is required")
    Integer quantity;


    @Size(max = 3, message = "Product variant must be less or equal than 2")
    @NotNull(message = "Product variant is required")
    private List<@Valid Variant> variants;

}

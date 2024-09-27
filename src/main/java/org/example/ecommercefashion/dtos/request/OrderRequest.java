package org.example.ecommercefashion.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderRequest {
    List<OrderItemRequest> orderItems;

    @Size(max = 255, message = "The length must be in range 0 to 255")
    private String description;

    @AssertTrue(message = "Order items cannot be null or empty")
    public boolean isOrderItemsValid() {
        return orderItems != null && !orderItems.isEmpty();
    }
}

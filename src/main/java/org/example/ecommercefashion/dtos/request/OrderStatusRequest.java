package org.example.ecommercefashion.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.annotations.EnumPattern;
import org.example.ecommercefashion.enums.OrderStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderStatusRequest {

    @EnumPattern(regexp = "PENDING|PROCESSING|SHIPPED|DELIVERED|CANCELLED|RETURNED", name = "status")
    private OrderStatus status;
}

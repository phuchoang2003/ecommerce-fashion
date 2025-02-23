package org.example.ecommercefashion.module.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.common.core.annotation.EnumPattern;
import org.example.ecommercefashion.module.order.enums.OrderStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderStatusRequest {

    @EnumPattern(regexp = "PENDING|PROCESSING|SHIPPED|DELIVERED|CANCELLED|RETURNED", name = "status")
    private OrderStatus status;
}

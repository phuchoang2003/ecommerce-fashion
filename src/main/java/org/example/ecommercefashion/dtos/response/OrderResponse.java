package org.example.ecommercefashion.dtos.response;

import lombok.Builder;
import org.example.ecommercefashion.entities.postgres.OrderDetail;
import org.example.ecommercefashion.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;


@Builder
public record OrderResponse(Long orderId,
                            Long userId,
                            String description,
                            Timestamp createdAt,
                            OrderStatus status,
                            BigDecimal total,
                            Set<OrderItemResponse> orderItems
) {
    public static OrderResponse fromEntity(OrderDetail orderDetail, Set<OrderItemResponse> orderItems) {
        return OrderResponse.builder()
                .orderId(orderDetail.getId())
                .userId(orderDetail.getUserId())
                .description(orderDetail.getDescription())
                .createdAt(orderDetail.getCreatedAt())
                .status(orderDetail.getStatus())
                .total(orderDetail.getTotal())
                .orderItems(orderItems)
                .build();
    }
}

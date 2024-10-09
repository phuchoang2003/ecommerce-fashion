package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.OrderRequest;
import org.example.ecommercefashion.dtos.response.OrderResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.OrderDetail;
import org.example.ecommercefashion.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

public interface OrderService {


    OrderResponse createOrder(OrderRequest request, Long userId);

    OrderResponse getOrderResponseById(Long orderId);

    OrderDetail getOrderDetailById(Long orderId);

    ResponsePage<OrderDetail, OrderResponse> filter(Pageable pageable);

    void updateOrderStatus(Long orderId, OrderStatus status);
}

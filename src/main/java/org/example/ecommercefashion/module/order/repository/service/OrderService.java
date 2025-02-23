package org.example.ecommercefashion.module.order.repository.service;

import org.example.ecommercefashion.module.order.dto.OrderRequest;
import org.example.ecommercefashion.module.product.dto.OrderResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.order.enums.OrderStatus;
import org.example.ecommercefashion.module.order.entity.OrderDetail;
import org.springframework.data.domain.Pageable;

public interface OrderService {


    OrderResponse createOrder(OrderRequest request, Long userId);

    OrderResponse getOrderResponseById(Long orderId);

    OrderDetail getOrderDetailById(Long orderId);

    ResponsePage<OrderDetail, OrderResponse> filter(Pageable pageable);

    void updateOrderStatus(Long orderId, OrderStatus status);
}

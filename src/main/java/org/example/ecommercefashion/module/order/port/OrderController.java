package org.example.ecommercefashion.module.order.port;


import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.auth.annotation.Protected;
import org.example.ecommercefashion.common.auth.annotation.RequestHeaderIdUser;
import org.example.ecommercefashion.module.order.dto.OrderRequest;
import org.example.ecommercefashion.module.order.dto.OrderStatusRequest;
import org.example.ecommercefashion.module.product.dto.OrderResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.common.auth.enums.TokenType;
import org.example.ecommercefashion.module.order.entity.OrderDetail;
import org.example.ecommercefashion.module.order.repository.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusRequest request) {
        orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok().build();
    }


    @PostMapping
    @Protected(TokenType.ACCESS)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request, @RequestHeaderIdUser Long userId) {
        return ResponseEntity.ok(orderService.createOrder(request, userId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderResponseById(id));
    }

    @GetMapping()
    public ResponseEntity<ResponsePage<OrderDetail, OrderResponse>> filter(Pageable pageable) {
        return ResponseEntity.ok(orderService.filter(pageable));
    }
}

package org.example.ecommercefashion.controllers;


import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.annotations.Protected;
import org.example.ecommercefashion.annotations.RequestHeaderIdUser;
import org.example.ecommercefashion.dtos.request.OrderRequest;
import org.example.ecommercefashion.dtos.request.OrderStatusRequest;
import org.example.ecommercefashion.dtos.response.OrderResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.OrderDetail;
import org.example.ecommercefashion.enums.TokenType;
import org.example.ecommercefashion.services.OrderService;
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
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusRequest status) {
        orderService.updateOrderStatus(id, status);
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

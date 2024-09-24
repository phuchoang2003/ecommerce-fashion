package org.example.ecommercefashion.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.CartItemRequest;
import org.example.ecommercefashion.dtos.request.UpdateCartItemRequest;
import org.example.ecommercefashion.dtos.response.CartResponse;
import org.example.ecommercefashion.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/carts")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getAllCartItem(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getAllCartItems(userId));
    }

    @DeleteMapping("/user/{userId}/cartItem/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> addItemToCart(@PathVariable Long userId, @RequestBody @Valid CartItemRequest request) {
        cartService.addItemToCart(userId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cartItem/{cartItemId}")
    public ResponseEntity<Void> updateItemInCart(@PathVariable Long cartItemId, @RequestBody @Valid UpdateCartItemRequest request) {
        cartService.updateItemInCart(cartItemId, request);
        return ResponseEntity.noContent().build();
    }


}

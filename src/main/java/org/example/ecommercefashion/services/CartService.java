package org.example.ecommercefashion.services;


import org.example.ecommercefashion.dtos.request.CartItemRequest;
import org.example.ecommercefashion.dtos.request.UpdateCartItemRequest;
import org.example.ecommercefashion.dtos.response.CartResponse;
import org.example.ecommercefashion.entities.postgres.Cart;
import org.example.ecommercefashion.entities.postgres.User;

public interface CartService {

    Cart getCartByUserId(Long userId);

    // them item vao gio hang
    // sua lai cache
    void addItemToCart(Long userId, CartItemRequest request);

    // xoa item vao gio hang
    // sua lai cache
    void removeItemFromCart(Long cartItemId, Long userId);

    // cap nhat item trong gio hang
    // sua lai cache
    void updateItemInCart(Long cartItemId, UpdateCartItemRequest request);

    // lay gio hang
    // luu vao cache
    CartResponse getAllCartItems(Long userId);

    // tao gio hang
    void create(User user);

    // xoa toan bo gio hang
    // xoa cache
    void clearCart(Long userId);
}

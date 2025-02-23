package org.example.ecommercefashion.module.cart.service;


import org.example.ecommercefashion.module.product.dto.CartItemRequest;
import org.example.ecommercefashion.module.product.dto.UpdateCartItemRequest;
import org.example.ecommercefashion.module.cart.dto.CartResponse;
import org.example.ecommercefashion.module.cart.entity.Cart;
import org.example.ecommercefashion.module.user.entity.User;

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

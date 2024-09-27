package org.example.ecommercefashion.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.dtos.request.CartItemRequest;
import org.example.ecommercefashion.dtos.request.UpdateCartItemRequest;
import org.example.ecommercefashion.dtos.response.CartItemResponse;
import org.example.ecommercefashion.dtos.response.CartResponse;
import org.example.ecommercefashion.entities.postgres.*;
import org.example.ecommercefashion.enums.ProductType;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.CartItemRepository;
import org.example.ecommercefashion.repositories.postgres.CartRepository;
import org.example.ecommercefashion.repositories.postgres.ProductVariantRepository;
import org.example.ecommercefashion.services.CartService;
import org.example.ecommercefashion.services.ImageService;
import org.example.ecommercefashion.services.ProductService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    private final ProductService productService;

    private final ProductVariantRepository productVariantRepository;

    private final CartItemRepository cartItemRepository;

    private final ImageService imageService;

    private final ObjectMapper mapper;

    @Autowired
    @Qualifier("genericRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    private String getKeyRedis(Long userId) {
        return "cart:" + userId;
    }


    @Async("ioTaskExecutor")
    @Retryable(
            value = {ExceptionHandle.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public void updateCacheAsync(Long userId) {
        log.info("Running on thread: {}", Thread.currentThread().getName());
        String key = getKeyRedis(userId);
        redisTemplate.delete(key);
        getAllCartItems(userId);
    }


    @Override
    @Async("ioTaskExecutor")
    @Retryable(
            value = {ExceptionHandle.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public void create(User user) {
        Cart cart = Cart.fromRequest(user);
        cartRepository.save(cart);
        log.info("Create cart successfully");
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository
                .findByUserId(userId).orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.CART_NOT_FOUND.val()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addItemToCart(Long userId, CartItemRequest request) {
        Cart cart = getCartByUserId(userId);
        ProductVariant variant = getProductVariantFromRequest(request);
        // cho phép null đến step này
        // nếu ở bước này là null thì sau đó sẽ lấu product từ productVariant
        Product product = getProductFromRequest(request, variant);
        CartItem cartItem = CartItem.fromRequest(cart, product, variant, request.getQuantity());
        cart.addItemToCart(cartItem);
        cart.calculateTotalAmount();
        // update async cache
        log.info("Running on thread: {}", Thread.currentThread().getName());
        CartServiceImpl proxy = (CartServiceImpl) AopContext.currentProxy();
        proxy.updateCacheAsync(userId);
    }


    private Product getProductFromRequest(CartItemRequest request, ProductVariant variant) {
        if (request.getType().equals(ProductType.PRODUCT)) {
            return productService.getById(request.getProductItemId());
        }
        return variant.getProduct();
    }

    private ProductVariant getProductVariantFromRequest(CartItemRequest request) {
        if (request.getType().equals(ProductType.PRODUCT_VARIANT)) {
            return productVariantRepository.findById(request.getProductItemId())
                    .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND,
                            ErrorMessage.PRODUCT_VARIANT_NOT_FOUND.val()));
        }
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeItemFromCart(Long cartItemId, Long userId) {
        Cart cart = getCartByUserId(userId);
        CartItem item = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.CART_ITEM_NOT_FOUND.val()));
        checkItemInCart(cart, item);
        cart.removeItemFromCart(item);
        cart.calculateTotalAmount();
        item.setDeleted(true);

        // update async cache
        log.info("Running on thread: {}", Thread.currentThread().getName());
        CartServiceImpl proxy = (CartServiceImpl) AopContext.currentProxy();
        proxy.updateCacheAsync(userId);
    }

    private void checkItemInCart(Cart cart, CartItem item) {
        if (!cart.getCartItems().contains(item)) {
            throw new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.CART_ITEM_NOT_FOUND.val());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateItemInCart(Long cartItemId, UpdateCartItemRequest request) {
        CartItem item = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.CART_ITEM_NOT_FOUND.val()));

        Product product = item.getProduct();
        Set<ProductVariant> productVariants = product.getProductVariants();
        ProductVariant variant = productVariants
                .stream()
                .filter(productVariant -> productVariant.getVariants().equals(new HashSet<>(request.getVariants())))
                .findFirst()
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.PRODUCT_VARIANT_NOT_FOUND.val()));

        item.setProductVariant(variant);
        item.setProductVariantId(variant.getId());
        // tính lại giá của cart và của cartItem
        item.setQuantity(request.getQuantity());
        item.setPrice(variant.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));

        Cart cart = item.getCart();
        cart.calculateTotalAmount();


        log.info("Running on thread: {}", Thread.currentThread().getName());
        CartServiceImpl proxy = (CartServiceImpl) AopContext.currentProxy();
        proxy.updateCacheAsync(cart.getUserId());

        cartItemRepository.save(item);
    }

    @Override
    public CartResponse getAllCartItems(Long userId) {
        String key = getKeyRedis(userId);
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            log.warn("cache hit");
            return mapper.convertValue(redisTemplate.opsForValue().get(key), CartResponse.class);
        }

        log.warn("cache miss");


        Cart cart = cartRepository.findDetailByUserId(userId).orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.CART_NOT_FOUND.val()));
        Set<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(item -> {
                    String url = item.getProduct()
                            .getProductImages()
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.IMAGE_NOT_FOUND.val()))
                            .getImage().getUrl();
                    return CartItemResponse.fromEntity(item, imageService.getUrlInStorageByUrlInDb(url));
                })
                .collect(Collectors.toSet());

        CartResponse response = CartResponse.fromEntity(cart, items, userId);

        redisTemplate.opsForValue().set(key, response, 30, TimeUnit.DAYS);

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getCartItems().forEach(cartItem -> cartItem.setDeleted(true));
        cart.removeAllItemsFromCart();
        cart.calculateTotalAmount();
        redisTemplate.delete(getKeyRedis(userId));
    }
}

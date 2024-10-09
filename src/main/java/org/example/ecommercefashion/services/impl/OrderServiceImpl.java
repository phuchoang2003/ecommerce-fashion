package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.OrderItemRequest;
import org.example.ecommercefashion.dtos.request.OrderRequest;
import org.example.ecommercefashion.dtos.response.OrderItemResponse;
import org.example.ecommercefashion.dtos.response.OrderResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.*;
import org.example.ecommercefashion.enums.OrderStatus;
import org.example.ecommercefashion.enums.ProductType;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.OrderRepository;
import org.example.ecommercefashion.repositories.postgres.ProductVariantRepository;
import org.example.ecommercefashion.services.ImageService;
import org.example.ecommercefashion.services.OrderService;
import org.example.ecommercefashion.services.ProductService;
import org.example.ecommercefashion.services.UserService;
import org.example.ecommercefashion.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;


    private final ProductService productService;
    private final ProductVariantRepository productVariantRepository;
    private final UserService userService;
    private final ImageService imageService;


    @Autowired
    @Qualifier("genericRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        OrderDetail orderDetail = orderRepository
                .findOrderDetailBriefById(orderId)
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.ORDER_NOT_FOUND.val()));
        orderDetail.setStatus(status);
    }

    @Override
    public ResponsePage<OrderDetail, OrderResponse> filter(Pageable pageable) {

        Page<OrderDetail> orderDetails = orderRepository.filter(pageable);
        return new ResponsePage<>(orderDetails.map(orderDetail -> OrderResponse.fromEntity(orderDetail, getOrderItemResponses(orderDetail.getOrderItems()))));
    }

    @Override
    public OrderDetail getOrderDetailById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.ORDER_NOT_FOUND.val()));
    }

    @Override
    public OrderResponse getOrderResponseById(Long orderId) {
        OrderDetail orderDetail = getOrderDetailById(orderId);
        Set<OrderItemResponse> orderItemResponses = getOrderItemResponses(orderDetail.getOrderItems());
        return OrderResponse.fromEntity(orderDetail, orderItemResponses);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponse createOrder(OrderRequest request, Long userId) {
        User user = userService.getUserById(userId);
        OrderDetail orderDetail = OrderDetail.fromRequest(request, user);
        Set<OrderItem> orderItems = getOrderItems(request.getOrderItems(), orderDetail);
        orderDetail.addOrderItems(orderItems);
        orderDetail.calculateTotal();
        orderRepository.save(orderDetail);
        Set<OrderItemResponse> orderItemResponses = getOrderItemResponses(orderItems);
        OrderResponse response = OrderResponse.fromEntity(orderDetail, orderItemResponses);

        // luu tren redis
        String key = "user:" + userId + "_orders:" + orderDetail.getId();
        redisTemplate.opsForValue().set(HashUtils.getMD5(key), response, 30, TimeUnit.DAYS);
        return response;
    }

    private Set<OrderItem> getOrderItems(List<OrderItemRequest> orderItemRequests, OrderDetail orderDetail) {
        return orderItemRequests.stream().map(orderItemRequest -> {
            ProductVariant variant = getProductVariantFromRequest(orderItemRequest);
            Product product = getProductFromRequest(orderItemRequest, variant);
            return OrderItem.fromRequest(orderItemRequest.getQuantity(), orderDetail, product, variant);
        }).collect(Collectors.toSet());
    }

    private Set<OrderItemResponse> getOrderItemResponses(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> {
                    String url = item.getProduct()
                            .getProductImages()
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.IMAGE_NOT_FOUND.val()))
                            .getImage()
                            .getUrl();
                    return OrderItemResponse.fromEntity(item, imageService.getUrlInStorageByUrlInDb(url));
                })
                .collect(Collectors.toSet());
    }

    private Product getProductFromRequest(OrderItemRequest request, ProductVariant variant) {
        if (request.getType().equals(ProductType.PRODUCT)) {
            return productService.getById(request.getProductItemId());
        }
        return variant.getProduct();
    }

    private ProductVariant getProductVariantFromRequest(OrderItemRequest request) {
        if (request.getType().equals(ProductType.PRODUCT_VARIANT)) {
            return productVariantRepository.findById(request.getProductItemId())
                    .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND,
                            ErrorMessage.PRODUCT_VARIANT_NOT_FOUND.val()));
        }
        return null;
    }
}

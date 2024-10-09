package org.example.ecommercefashion.services.impl;


import com.google.gson.JsonSyntaxException;
import com.longnh.exceptions.ExceptionHandle;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.dtos.response.PaymentResponse;
import org.example.ecommercefashion.entities.postgres.OrderDetail;
import org.example.ecommercefashion.entities.postgres.PaymentTransaction;
import org.example.ecommercefashion.entities.postgres.User;
import org.example.ecommercefashion.enums.TokenType;
import org.example.ecommercefashion.enums.TransactionStatus;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.PaymentTransactionRepository;
import org.example.ecommercefashion.security.JwtUtils;
import org.example.ecommercefashion.services.*;
import org.example.ecommercefashion.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final OrderService orderService;
    private final ScheduledExecutorService scheduler;
    private final ProductService productService;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final WebhookStripeService webhookStripeService;


    @Autowired
    private PaymentServiceImpl self;


    @Autowired
    @Qualifier("genericRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${stripe.signing.secret}")
    private String endpointSecret;


    @Override
    public PaymentTransaction findPaymentTransactionById(Long paymentTransactionId) {
        return paymentTransactionRepository.findById(paymentTransactionId).orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.PAYMENT_TRANSACTION_NOT_FOUND.val()));
    }

    @Override
    public PaymentTransaction findByPaymentIntentId(String paymentIntentId) {
        return paymentTransactionRepository
                .findByPaymentIntentId(paymentIntentId).orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.PAYMENT_TRANSACTION_NOT_FOUND.val()));
    }

    private Map<String, String> createMetadataPaymentIntent(OrderDetail orderDetail) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("order_id", String.valueOf(orderDetail.getId()));
        return metadata;
    }

    public Customer createCustomer(String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        // token la thong tin thanh toan tam thoi cau nguoi dung
        Customer customer = Customer.create(customerParams);
        // update lại trong database
        User user = userService.getUserByEmail(email);
        user.setStripeCustomerId(customer.getId());
        return customer;
    }


    private void isOwnerOrder(Long orderId, Long userId) {
        String key = "user:" + userId + "_orders:" + orderId;
        Object object = redisTemplate.opsForValue().get(HashUtils.getMD5(key));
        if (object == null) {
            throw new ExceptionHandle(HttpStatus.FORBIDDEN, ErrorMessage.FORBIDDEN.val());
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void checkPaymentTransactionStatus(Long paymentTransactionId, OrderDetail orderDetail) {
        PaymentTransaction paymentTransaction = findPaymentTransactionById(paymentTransactionId);
        if (!paymentTransaction.getStatus().equals(TransactionStatus.PENDING)) return;

        if (paymentTransaction.getExpiredAt().before(new Timestamp(System.currentTimeMillis()))) {
            paymentTransaction.setStatus(TransactionStatus.EXPIRED);
            // giai phong so luong san pham
            productService.processQuantityProduct(orderDetail, true);
        }
    }

    @Override
    public PaymentTransaction createPaymentTransaction(Long userId, OrderDetail orderDetail, PaymentIntent paymentIntent) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setUserId(userId);
        paymentTransaction.setOrderId(orderDetail.getId());
        paymentTransaction.setOrderDetail(orderDetail);
        paymentTransaction.setExpiredAt(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 5));
        paymentTransaction.setAmount(orderDetail.getTotal());
        paymentTransaction.setPaymentIntentId(paymentIntent.getId());
        return paymentTransactionRepository.save(paymentTransaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentResponse checkoutOrder(Long orderId, String token, String idempotencyKey) {
        // goi service order
        Claims claims = jwtUtils.extractAllClaims(token, TokenType.ACCESS);
        Long userId = Long.parseLong(claims.get(JwtUtils.ClaimKey.USER_ID.val).toString());
        // check quyen
        isOwnerOrder(orderId, userId);

        String customerStripeId = claims.get(JwtUtils.ClaimKey.CUSTOMER_STRIPE_ID.val).toString();
        try {
            if (customerStripeId.equals("null")) {
                Customer customer = createCustomer(claims.get(JwtUtils.ClaimKey.EMAIL.val).toString());
                customerStripeId = customer.getId();
            }

            OrderDetail orderDetail = orderService.getOrderDetailById(orderId);
            // reservation so luong san pham
            productService.processQuantityProduct(orderDetail, false);
            // tao Payment Intent
            PaymentIntent paymentIntent = createPaymentIntent(orderDetail.getTotal(), idempotencyKey, customerStripeId, createMetadataPaymentIntent(orderDetail));

            // tao paymentTransaction
            PaymentTransaction paymentTransaction = createPaymentTransaction(userId, orderDetail, paymentIntent);
            scheduler.schedule(() -> self.checkPaymentTransactionStatus(paymentTransaction.getId(), orderDetail), 5, TimeUnit.MINUTES);

            return PaymentResponse.fromEntity(orderDetail, paymentIntent);


        } catch (Exception e) {
            log.error("Unexpected error during payment processing: {}", e.getMessage());
            throw new ExceptionHandle(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SOMETHING_WENT_WRONG.val());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleWebhook(String payload, String signHeader) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, signHeader, endpointSecret);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Invalid payload");
        } catch (SignatureVerificationException e) {
            throw new RuntimeException("Invalid signature");
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().orElseThrow(() ->
                new ExceptionHandle(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SOMETHING_WENT_WRONG.val()));

        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;


        switch (event.getType()) {
            case "payment_intent.succeeded":
                webhookStripeService.handleSuccess(paymentIntent);
                break;
            case "payment_intent.payment_failed":
                self.scheduler.schedule(() -> webhookStripeService.handleFailure(paymentIntent), 5, TimeUnit.MINUTES);
                webhookStripeService.handleFailure(paymentIntent);
                break;
            case "payment_intent.canceled":
                webhookStripeService.handleCanceled(paymentIntent);
                break;
            default:

                break;
        }
    }

    public PaymentIntent createPaymentIntent(BigDecimal amount, String idempotencyKey, String customerStripeId, Map<String, String> metadata) throws Exception {
        // kiem tra customer_stripe_id dua vao access token

        Long amountInCents = amount.multiply(new BigDecimal(100)).longValue();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("usd")
                .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
                .putAllMetadata(metadata)
                .setCustomer(customerStripeId)
                .build();

        RequestOptions requestOptions = RequestOptions.builder()
                .setIdempotencyKey(idempotencyKey)
                .build();

        return PaymentIntent.create(params, requestOptions);
    }


    public enum MetaDataPaymentIntent {
        ORDER_ID("order_id");
        public final String val;

        MetaDataPaymentIntent(String val) {
            this.val = val;
        }
    }
}




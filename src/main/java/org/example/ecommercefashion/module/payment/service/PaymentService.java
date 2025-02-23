package org.example.ecommercefashion.module.payment.service;

import com.stripe.model.PaymentIntent;
import org.example.ecommercefashion.module.payment.dto.PaymentResponse;
import org.example.ecommercefashion.module.order.entity.OrderDetail;
import org.example.ecommercefashion.module.payment.entity.PaymentTransaction;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentService {

    PaymentIntent createPaymentIntent(BigDecimal amount, String idempotencyKey, String customerStripeId, Map<String, String> metadata) throws Exception;

    void handleWebhook(String payload, String signHeader);


    PaymentResponse checkoutOrder(Long orderId, String token, String idempotencyKey);

    PaymentTransaction createPaymentTransaction(Long userId, OrderDetail orderDetail, PaymentIntent paymentIntent);

    PaymentTransaction findByPaymentIntentId(String paymentIntentId);

    PaymentTransaction findPaymentTransactionById(Long paymentTransactionId);
}

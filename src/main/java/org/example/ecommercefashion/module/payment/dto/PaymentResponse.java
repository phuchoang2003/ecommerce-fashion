package org.example.ecommercefashion.module.payment.dto;

import com.stripe.model.PaymentIntent;
import lombok.Builder;
import org.example.ecommercefashion.module.order.enums.OrderStatus;
import org.example.ecommercefashion.module.order.entity.OrderDetail;

import java.math.BigDecimal;


@Builder
public record PaymentResponse(String clientSecret,
                              BigDecimal totalAmount,
                              Long orderId,
                              String paymentIntentId,
                              String currency,
                              OrderStatus orderStatus) {

    public static PaymentResponse fromEntity(OrderDetail orderDetail, PaymentIntent paymentIntent) {
        return PaymentResponse.builder()
                .clientSecret(paymentIntent.getClientSecret())
                .totalAmount(orderDetail.getTotal())
                .orderId(orderDetail.getId())
                .paymentIntentId(paymentIntent.getId())
                .currency(paymentIntent.getCurrency())
                .orderStatus(orderDetail.getStatus())
                .build();
    }
}

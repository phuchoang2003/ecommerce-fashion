package org.example.ecommercefashion.dtos.response;

import com.stripe.model.PaymentIntent;
import lombok.Builder;
import org.example.ecommercefashion.entities.postgres.OrderDetail;
import org.example.ecommercefashion.enums.OrderStatus;

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

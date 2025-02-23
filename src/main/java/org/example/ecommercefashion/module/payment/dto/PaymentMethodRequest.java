package org.example.ecommercefashion.module.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PaymentMethodRequest {

    @NotBlank(message = "Client Secret is required")
    private String clientSecret;

    @NotBlank(message = "Payment Method ID is required")
    private String paymentMethodId;

    @NotBlank(message = "Payment Intent ID is required")
    private String paymentIntentId;
}

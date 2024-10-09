package org.example.ecommercefashion.dtos.request;


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

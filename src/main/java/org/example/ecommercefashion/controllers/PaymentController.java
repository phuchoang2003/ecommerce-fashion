package org.example.ecommercefashion.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.annotations.Protected;
import org.example.ecommercefashion.dtos.response.PaymentResponse;
import org.example.ecommercefashion.enums.TokenType;
import org.example.ecommercefashion.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("webhooks")
    public void handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signHeader) {
        paymentService.handleWebhook(payload, signHeader);
    }


    @Protected(TokenType.ACCESS)
    @PostMapping("checkout/{orderId}")
    public ResponseEntity<PaymentResponse> checkoutOrder(@PathVariable Long orderId,
                                                         @RequestHeader("Authorization") String token,
                                                         @RequestHeader("Idempotency-Key") String idempotencyKey) {
        return ResponseEntity.ok().body(paymentService.checkoutOrder(orderId, token, idempotencyKey));
    }
}

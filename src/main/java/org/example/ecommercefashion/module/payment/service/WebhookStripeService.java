package org.example.ecommercefashion.module.payment.service;

import com.stripe.model.PaymentIntent;

public interface WebhookStripeService {
    void handleSuccess(PaymentIntent paymentIntent);

    void handleFailure(PaymentIntent paymentIntent);


    void handleCanceled(PaymentIntent paymentIntent);


}

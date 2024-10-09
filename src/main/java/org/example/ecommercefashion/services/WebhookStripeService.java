package org.example.ecommercefashion.services;

import com.stripe.model.PaymentIntent;

public interface WebhookStripeService {
    void handleSuccess(PaymentIntent paymentIntent);

    void handleFailure(PaymentIntent paymentIntent);


    void handleCanceled(PaymentIntent paymentIntent);


}

package org.example.ecommercefashion.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeClient {


    @Autowired
    public StripeClient(@Value("${stripe.secret.key}") String secretKey) {
        Stripe.apiKey = secretKey;
    }


}

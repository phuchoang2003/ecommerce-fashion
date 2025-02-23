package org.example.ecommercefashion.module.payment.repository;

import org.example.ecommercefashion.module.payment.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    Optional<PaymentTransaction> findByPaymentIntentId(String paymentIntentId);
}

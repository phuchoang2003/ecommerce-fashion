package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.entities.postgres.PaymentTransaction;
import org.example.ecommercefashion.enums.OrderStatus;
import org.example.ecommercefashion.enums.TransactionStatus;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.services.OrderService;
import org.example.ecommercefashion.services.PaymentService;
import org.example.ecommercefashion.services.ProductService;
import org.example.ecommercefashion.services.WebhookStripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class WebhookStripeServiceImpl implements WebhookStripeService {
    private static final List<String> RETRYABLE_ERRORS = Arrays.asList(
            "card error",           // Lỗi liên quan đến thẻ
            "card declined",        // Thẻ bị từ chối
            "cvv mismatch",         // Mã CVV không khớp
            "address mismatch",     // Địa chỉ không khớp
            "temporary server error", // Lỗi tạm thời từ server
            "service unavailable",   // Dịch vụ không khả dụng
            "transaction timed out"  // Giao dịch đã hết thời gian
    );
    private final OrderService orderService;
    private final ProductService productService;
    private PaymentService paymentService;

    public void setPaymentService(@Autowired PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private void checkTimeOutPaymentSession(PaymentTransaction paymentTransaction) {
        if (paymentTransaction.getExpiredAt().getTime() < System.currentTimeMillis()) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.PAYMENT_SESSION_TIME_OUT.val());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void handleSuccess(PaymentIntent paymentIntent) {
        PaymentTransaction paymentTransaction = paymentService.findByPaymentIntentId(paymentIntent.getId());

        checkTimeOutPaymentSession(paymentTransaction);

        // capture payment Intent
        try {
            paymentIntent.capture();
        } catch (Exception e) {
            throw new ExceptionHandle(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SOMETHING_WENT_WRONG.val());
        }

        Long orderId = Long.parseLong(paymentIntent.getMetadata().get(PaymentServiceImpl.MetaDataPaymentIntent.ORDER_ID.val));


        // doi trang thai order -> Processing
        orderService.updateOrderStatus(orderId, OrderStatus.PROCESSING);

        // cap nhat payment transaction
        paymentTransaction.setStatus(TransactionStatus.COMPLETED);
        paymentTransaction.setCurrency(paymentIntent.getCurrency());
        paymentTransaction.setPaymentMethod(paymentIntent.getPaymentMethodTypes().get(0));
        paymentTransaction.setCompletedAt(new Timestamp(System.currentTimeMillis()));

        // gui mail
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleCanceled(PaymentIntent paymentIntent) {
        Long orderId = Long.parseLong(paymentIntent.getMetadata().get(PaymentServiceImpl.MetaDataPaymentIntent.ORDER_ID.val));
        productService.processQuantityProduct(orderService.getOrderDetailById(orderId), false);
        PaymentTransaction paymentTransaction = paymentService.findByPaymentIntentId(paymentIntent.getId());
        paymentTransaction.setStatus(TransactionStatus.CANCELLED);
        try {
            paymentIntent.cancel();
            // gui mail
        } catch (Exception e) {
            throw new ExceptionHandle(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SOMETHING_WENT_WRONG.val());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleFailure(PaymentIntent paymentIntent) {
        PaymentTransaction paymentTransaction = paymentService.findByPaymentIntentId(paymentIntent.getId());
        int retryCount = paymentTransaction.getRetryAttempt();

        StripeError error = paymentIntent.getLastPaymentError();
        String failureReason = error != null ? error.getMessage() : "Unknown error";

        if (RETRYABLE_ERRORS.stream().anyMatch(failureReason::contains) &&
                retryCount < paymentTransaction.getMaxRetryAttempt()) {
            paymentTransaction.setRetryAttempt((byte) (retryCount + 1));
            paymentTransaction.setExpiredAt(addExtraTime(paymentTransaction.getExpiredAt()));
            paymentTransaction.setReasonFailed(paymentTransaction.getReasonFailed() + "\n" + failureReason);
        } else {
            try {
                Long orderId = Long.parseLong(paymentIntent.getMetadata().get(PaymentServiceImpl.MetaDataPaymentIntent.ORDER_ID.val));
                paymentTransaction.setStatus(TransactionStatus.FAILED);
                paymentIntent.cancel();
                // release lai so luong san pham
                productService.processQuantityProduct(orderService.getOrderDetailById(orderId), false);

                // gui mail
            } catch (Exception e) {
                throw new ExceptionHandle(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SOMETHING_WENT_WRONG.val());
            }
        }
    }


    private Timestamp addExtraTime(Timestamp expiredTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(expiredTime.getTime());
        calendar.add(Calendar.MINUTE, 5);
        return new Timestamp(calendar.getTimeInMillis());
    }

}

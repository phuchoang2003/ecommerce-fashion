package org.example.ecommercefashion.module.email.port;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.module.email.entity.Email;
import org.example.ecommercefashion.module.email.service.EmailService;
import org.example.ecommercefashion.module.order.entity.OrderDetail;
import org.example.ecommercefashion.module.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/emails")
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Email> sendEmail() {

        OrderDetail orderDetail = orderRepository.findById(40L).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(emailService.createEmail(2L, "5cTqE@example.com", "5cTqE@example.com", orderDetail));
    }
}

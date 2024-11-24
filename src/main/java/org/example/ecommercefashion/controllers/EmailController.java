package org.example.ecommercefashion.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.entities.postgres.Email;
import org.example.ecommercefashion.entities.postgres.OrderDetail;
import org.example.ecommercefashion.repositories.postgres.OrderRepository;
import org.example.ecommercefashion.services.EmailService;
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

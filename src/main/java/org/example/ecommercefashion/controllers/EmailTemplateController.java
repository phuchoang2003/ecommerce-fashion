package org.example.ecommercefashion.controllers;


import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.EmailTemplateRequest;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.EmailTemplate;
import org.example.ecommercefashion.services.EmailTemplateService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/email_templates")
@RestController
@RequiredArgsConstructor
public class EmailTemplateController {
    private final EmailTemplateService emailTemplateService;


    @RequestMapping
    public ResponseEntity<ResponsePage<EmailTemplate, EmailTemplate>> findAll(Pageable pageable) {
        return ResponseEntity.ok(emailTemplateService.getAllTemplate(pageable));
    }


    @RequestMapping("{id}")
    public ResponseEntity<EmailTemplate> findById(@PathVariable Long id) {
        return ResponseEntity.ok(emailTemplateService.get(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        emailTemplateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<EmailTemplate> createEmailTemplate(@Valid @RequestBody EmailTemplateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emailTemplateService.create(request));
    }

    @PostMapping("{id}")
    public ResponseEntity<Void> chooseEmailTemplate(@PathVariable Long id) {
        emailTemplateService.chooseEmailTemplate(id);
        return ResponseEntity.noContent().build();
    }

}

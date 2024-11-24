package org.example.ecommercefashion.controllers;


import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.services.EmailPlaceHolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/email_place_holders")
@RestController
@RequiredArgsConstructor
public class EmailPlaceHolderController {
    private final EmailPlaceHolderService emailPlaceHolderService;


    @PostMapping
    public ResponseEntity<Void> createPlaceHolder() {
        emailPlaceHolderService.createPlaceHolder();
        return ResponseEntity.noContent().build();
    }
}

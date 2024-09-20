package org.example.ecommercefashion.controllers;


import org.example.ecommercefashion.dtos.request.AttributeRequest;
import org.example.ecommercefashion.dtos.request.UpdateAttributeRequest;
import org.example.ecommercefashion.dtos.response.AttributeResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.Attribute;
import org.example.ecommercefashion.services.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/attributes")
@RestController
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @PostMapping
    public ResponseEntity<AttributeResponse> createAttribute(@Valid @RequestBody AttributeRequest request) {
        AttributeResponse response = attributeService.createAttribute(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("{id}")
    public ResponseEntity<AttributeResponse> findById(@PathVariable Long id) {
        AttributeResponse response = attributeService.getAttributeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponsePage<Attribute, AttributeResponse>> findAll(Pageable pageable) {

        return ResponseEntity.ok(attributeService.filter(pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        attributeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<AttributeResponse> updateById(@PathVariable Long id, @Valid @RequestBody UpdateAttributeRequest data) {
        AttributeResponse response = attributeService.updateById(id, data);
        return ResponseEntity.ok(response);
    }
}

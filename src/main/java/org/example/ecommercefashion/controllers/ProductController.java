package org.example.ecommercefashion.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.ProductRequest;
import org.example.ecommercefashion.dtos.response.ProductBriefResponse;
import org.example.ecommercefashion.dtos.response.ProductDetailResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.Product;
import org.example.ecommercefashion.services.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDetailResponse> create(@RequestPart(value = "request") @Valid ProductRequest request,
                                                        @RequestPart(value = "files") List<MultipartFile> files) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request, files));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDetailResponse> update(@RequestPart(value = "request") @Valid ProductRequest request,
                                                        @RequestPart(value = "files") List<MultipartFile> files,
                                                        @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.update(request, files, id));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductResponseById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ResponsePage<Product, ProductBriefResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }
}

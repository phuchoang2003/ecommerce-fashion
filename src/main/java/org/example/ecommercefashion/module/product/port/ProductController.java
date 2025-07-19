package org.example.ecommercefashion.module.product.port;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.product.dto.ProductBriefResponse;
import org.example.ecommercefashion.module.product.dto.ProductDetailResponse;
import org.example.ecommercefashion.module.product.dto.ProductFilter;
import org.example.ecommercefashion.module.product.dto.ProductRequest;
import org.example.ecommercefashion.module.product.entity.Product;
import org.example.ecommercefashion.module.product.service.ProductService;
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
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ResponsePage<Product, ProductBriefResponse>> findAll(@ModelAttribute ProductFilter filter, Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(filter, pageable));
    }

//    @PostMapping("test-concurrent")
//    @Protected(TokenType.ACCESS)
//    public ResponseEntity<?> testConcurrent(@RequestBody @Valid OrderRequest request, @RequestHeaderIdUser Long userId) {
//        OrderDetail orderDetail = orderService.createOrderEntity(request, userId);
//        productService.processQuantityProduct(orderDetail.getId());
//        System.out.println("Ping");
//        return ResponseEntity.ok().build();
//    }
}

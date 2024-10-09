package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.ProductRequest;
import org.example.ecommercefashion.dtos.response.ProductBriefResponse;
import org.example.ecommercefashion.dtos.response.ProductDetailResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.OrderDetail;
import org.example.ecommercefashion.entities.postgres.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDetailResponse create(ProductRequest request, List<MultipartFile> files);

    ProductDetailResponse findProductResponseById(Long id);

    Product getById(Long id);

    void deleteById(Long id);

    ResponsePage<Product, ProductBriefResponse> findAll(Pageable pageable);

    ProductDetailResponse update(ProductRequest request, List<MultipartFile> files, Long id);

    void processQuantityProduct(OrderDetail orderDetail, boolean isIncreased);

}

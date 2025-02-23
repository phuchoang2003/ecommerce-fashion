package org.example.ecommercefashion.module.product.service;

import org.example.ecommercefashion.module.product.dto.ProductRequest;
import org.example.ecommercefashion.module.product.dto.ProductBriefResponse;
import org.example.ecommercefashion.module.product.dto.ProductDetailResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.product.entity.Product;
import org.example.ecommercefashion.module.order.entity.OrderDetail;
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

package org.example.ecommercefashion.module.product.service;

import org.example.ecommercefashion.module.product.dto.CategoryRequest;
import org.example.ecommercefashion.module.product.dto.CategoryResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.product.entity.Category;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    Category getById(Long id);

    CategoryResponse findById(Long id);

    CategoryResponse updateById(@Valid CategoryRequest request, Long id);

    ResponsePage<Category, CategoryResponse> findAllCategory(Pageable pageable);


    void deleteCategoryById(Long id);
}

package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.CategoryRequest;
import org.example.ecommercefashion.dtos.response.CategoryResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.Category;
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

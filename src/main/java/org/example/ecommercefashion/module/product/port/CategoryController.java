package org.example.ecommercefashion.module.product.port;

import org.example.ecommercefashion.module.product.dto.CategoryRequest;
import org.example.ecommercefashion.module.product.dto.CategoryResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.product.entity.Category;
import org.example.ecommercefashion.module.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/categories")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        CategoryResponse response = categoryService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CategoryResponse> updateById(@Valid @RequestBody CategoryRequest request, @PathVariable Long id) {
        CategoryResponse response = categoryService.updateById(request, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponsePage<Category, CategoryResponse>> findAll(Pageable pageable) {

        return ResponseEntity.ok(categoryService.findAllCategory(pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok().build();
    }

}

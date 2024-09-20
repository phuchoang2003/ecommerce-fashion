package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.dtos.request.CategoryRequest;
import org.example.ecommercefashion.dtos.response.CategoryResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.Attribute;
import org.example.ecommercefashion.entities.postgres.Category;
import org.example.ecommercefashion.entities.postgres.RelCategoryAttribute;
import org.example.ecommercefashion.entities.postgres.SizeChart;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.AttributeRepository;
import org.example.ecommercefashion.repositories.postgres.CategoryRepository;
import org.example.ecommercefashion.repositories.postgres.SizeChartRepository;
import org.example.ecommercefashion.services.CategoryService;
import org.example.ecommercefashion.utils.SlugUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final SizeChartRepository sizeChartRepository;

    private final AttributeRepository attributeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteCategoryById(Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
        updateValueAfterDelete(category.getLeftValue(), category.getRightValue());
    }

    private void updateValueAfterDelete(long leftVal, long rightVal) {
        long distance = rightVal - leftVal + 1;
        categoryRepository.updateLeftValueAfterDelete(distance, rightVal);
        categoryRepository.updateRightValueAfterDelete(distance, rightVal);
    }


    @Override
    public ResponsePage<Category, CategoryResponse> findAllCategory(Pageable pageable) {
        Page<CategoryResponse> responses = categoryRepository.filter(pageable).map(CategoryResponse::fromModel);
        return new ResponsePage<>(responses);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryResponse updateById(CategoryRequest request, Long id) {
        checkSizeChartIds(request.getSupportSizeChartIds());
        checkDuplicateSlug(request.getName());
        Category category = getById(id);
        category.setName(request.getName());
        category.setSlug(SlugUtils.generateSlug(request.getName()));
        category.setSupportSizeChartIds(new ArrayList<>(request.getSupportSizeChartIds()));
        return CategoryResponse.fromModel(category);
    }

    @Override
    public CategoryResponse findById(Long id) {
        Category category = getById(id);
        return CategoryResponse.fromModel(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findBy(id).orElseThrow(() ->
                new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND_CATEGORY.val()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class,
            isolation = Isolation.SERIALIZABLE,
            timeout = 10,
            propagation = Propagation.REQUIRED)
    public CategoryResponse createCategory(CategoryRequest request) {
        checkDuplicateSlug(request.getName());
        checkSizeChartIds(request.getSupportSizeChartIds());

        Set<Attribute> attributes = attributeRepository.findByIdIn(request.getAttributeIds());
        checkAttributeIds(attributes, request.getAttributeIds());
        long maxValue = calculateMaxValue(request.getParentId());
        updateParentCategoryValues(maxValue);
        Category parent = getParent(request.getParentId());
        Category category = categoryRepository.save(Category.fromRequest(request, parent, calculateLevel(parent), maxValue));
        category.setRelCategoryAttributes(
                attributes.stream()
                        .map(attribute -> new RelCategoryAttribute(attribute, category))
                        .collect(Collectors.toSet()));

        return CategoryResponse.fromModel(category);
    }


    private void checkAttributeIds(Set<Attribute> attributes, Set<Long> ids) {
        Set<Long> existsIds = attributes.stream().map(Attribute::getId).collect(Collectors.toSet());
        for (Long id : ids) {
            if (!existsIds.contains(id)) {
                throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.ATTRIBUTE_NOT_FOUND.val() + " with id: " + id);
            }
        }
    }

    private void checkSizeChartIds(Set<Long> ids) {
        Set<Long> existsIds = sizeChartRepository.findByIdIn(ids)
                .stream()
                .map(SizeChart::getId).collect(Collectors.toSet());
        for (Long id : ids) {
            if (!existsIds.contains(id)) {
                throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.SIZE_CHART_IDS_NOT_FOUND.val() + " with id: " + id);
            }
        }
    }

    private void checkDuplicateSlug(String name) {
        String slug = SlugUtils.generateSlug(name);
        if (categoryRepository.existsBySlug(slug)) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.DUPLICATE_CATEGORY.val());
        }
    }

    private Category getParent(Long id) {
        return id == null ? null : getById(id);
    }

    private int calculateLevel(Category parent) {
        return parent == null ? 0 : parent.getLevel() + 1;
    }


    private long calculateMaxValue(Long parentId) {
        if (parentId == null) {
            Long maxValue = categoryRepository.getMaxValue();
            return maxValue != null ? maxValue + 1 : 1;
        } else {
            return getParent(parentId).getRightValue();
        }
    }


    private void updateParentCategoryValues(long val) {
        categoryRepository.updateLeftValue(val);
        categoryRepository.updateRightValue(val);
    }


}

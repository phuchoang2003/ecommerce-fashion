package org.example.ecommercefashion.dtos.response;


import lombok.Builder;
import org.example.ecommercefashion.entities.postgres.Category;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Builder
public record CategoryResponse(Long id,
                               String name,
                               Long parentId,
                               Set<CategoryResponse> subCategories,
                               Integer level,
                               Long leftValue,
                               Long rightValue,
                               String slug,
                               List<Long> supportSizeChartIds) {

    public static CategoryResponse fromModel(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParentId())
                .subCategories(category.getSubCategories().stream().map(CategoryResponse::fromModel).collect(Collectors.toSet()))
                .level(category.getLevel())
                .leftValue(category.getLeftValue())
                .rightValue(category.getRightValue())
                .slug(category.getSlug())
                .supportSizeChartIds(category.getSupportSizeChartIds())
                .build();
    }
}

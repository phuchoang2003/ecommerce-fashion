package org.example.ecommercefashion.dtos.response;

import lombok.Builder;
import org.example.ecommercefashion.dtos.projection.ProductBriefDTO;
import org.example.ecommercefashion.enums.ProductState;

import java.math.BigDecimal;


@Builder
public record ProductBriefResponse(Long id,
                                   String name,
                                   String slug,
                                   String urlMainImage,
                                   BigDecimal price,
                                   Integer quantity,
                                   String categoryName,
                                   ProductState state) {

    public static ProductBriefResponse fromDto(ProductBriefDTO dto, String urlMainImage) {
        return ProductBriefResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .slug(dto.getSlug())
                .urlMainImage(urlMainImage)
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .categoryName(dto.getCategoryName())
                .state(dto.getState())
                .build();
    }
}

package org.example.ecommercefashion.module.product.dto;

import lombok.Builder;
import org.example.ecommercefashion.common.storage.dto.ImageResponse;
import org.example.ecommercefashion.module.product.enums.ProductState;
import org.example.ecommercefashion.module.product.entity.Category;
import org.example.ecommercefashion.module.product.entity.Product;
import org.example.ecommercefashion.module.product.entity.ProductVariant;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Builder
public record ProductDetailResponse(Long id, String name, String description, BigDecimal price, Integer quantity,
                                    ProductState state,
                                    String slug, Category category, Map<String, String> attributeValues,
                                    Set<ProductVariant> productVariants,
                                    List<ImageResponse> images, Set<SizeChartResponse> sizeChartResponses) {

    public static ProductDetailResponse fromEntity(Product product, List<ImageResponse> images, Set<SizeChartResponse> sizeChartResponses) {
        return ProductDetailResponse.builder()
                .id(product.getId())
                .category(product.getCategory())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .state(product.getState())
                .slug(product.getSlug())
                .attributeValues(product.getRelProductAttributeValues()
                        .stream()
                        .map(rel -> Map.entry(rel.getAttributeValue().getAttribute().getDisplayKey(), rel.getAttributeValue().getDisplayValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .productVariants(product.getProductVariants())
                .images(images)
                .sizeChartResponses(sizeChartResponses)
                .build();
    }

}

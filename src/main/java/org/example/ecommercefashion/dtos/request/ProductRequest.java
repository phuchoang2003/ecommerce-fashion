package org.example.ecommercefashion.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.annotations.EnumPattern;
import org.example.ecommercefashion.entities.postgres.Variant;
import org.example.ecommercefashion.enums.ProductState;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 255, message = "Product name must be between 2 and 255 characters")
    private String name;

    @NotBlank(message = "Product description is required")
    @Size(min = 2, max = 2000, message = "Product description must be between 2 and 2000 characters")
    private String description;

    @Positive(message = "Product price must be greater than 0")
    private BigDecimal price;

    @PositiveOrZero(message = "Product quantity must be greater or equal than 0")
    @NotNull(message = "Product quantity is required")
    private Integer quantity;

    @NotNull(message = "Product category is required")
    @Positive(message = "Category id must be greater than 0")
    private Long categoryId;

    @EnumPattern(regexp = "AVAILABLE|OUT_OF_STOCK|DISCONTINUED|PRE_ORDER|PENDING", name = "state")
    private ProductState state;

    @NotEmpty(message = "Product attribute value is required")
    @Size(min = 1, max = 10, message = "Product attribute value must be less than 10")
    @NotNull(message = "Product attribute value is required")
    private Set<@Positive(message = "Attribute value id must be greater than 0") Long> attributeValueIds;

    @Size(max = 3, message = "Product variant must be less or equal than 2")
    @NotNull(message = "Product variant is required")
    private List<@Valid Variant> variants;


    @NotNull(message = "Support size chart ids cannot be null")
    @Size(min = 1, max = 10, message = "The number of size charts must be between 1 and 10.")
    private Set<@Positive(message = "Id must be greater than 0") Long> supportSizeChartIds;


    @AssertTrue(message = "Product variant must be unique")
    public boolean isVariantUnique() {
        return variants.size() == variants.stream()
                .map(variant -> variant.getName()
                        .toLowerCase())
                .distinct()
                .count();
    }
}

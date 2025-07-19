package org.example.ecommercefashion.module.product.dto;


import lombok.Getter;
import lombok.Setter;
import org.example.ecommercefashion.module.product.enums.ProductState;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductFilter {
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private ProductState state;
    private String categoryName;

}

package org.example.ecommercefashion.dtos.projection;

import org.example.ecommercefashion.enums.ProductState;

import java.math.BigDecimal;
import java.util.List;

public interface ProductBriefDTO {

    Long getId();

    String getCategoryName();

    String getName();

    String getSlug();

    BigDecimal getPrice();

    ProductState getState();

    Integer getQuantity();

    List<String> getUrls();
}

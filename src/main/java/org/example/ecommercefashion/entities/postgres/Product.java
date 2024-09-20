package org.example.ecommercefashion.entities.postgres;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.example.ecommercefashion.dtos.request.ProductRequest;
import org.example.ecommercefashion.enums.ProductState;
import org.example.ecommercefashion.utils.SlugUtils;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "products")
@Entity
@TypeDef(
        name = "long-array",
        typeClass = ListArrayType.class
)
public class Product extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private ProductState state = ProductState.PENDING;

    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE})
    private Set<RelProductAttributeValue> relProductAttributeValues;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private Set<ProductVariant> productVariants;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE})
    private Set<ProductImage> productImages;


    @Column(name = "support_size_chart_ids", columnDefinition = "bigint[]")
    @Type(type = "long-array")
    private List<Long> supportSizeChartIds;


    @Column(name = "slug")
    private String slug;

    public static Product fromRequest(ProductRequest request, Category category) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .state(request.getState())
                .categoryId(category.getId())
                .category(category)
                .slug(SlugUtils.generateSlug(request.getName()))
                .supportSizeChartIds(new ArrayList<>(request.getSupportSizeChartIds()))
                .build();
    }


    public void removeProductVariants(Set<ProductVariant> productVariant) {
        if (this.productVariants == null) {
            return;
        }
        this.productVariants.removeAll(productVariant);
    }

    public void addAttributeValues(Set<RelProductAttributeValue> relProductAttributeValues) {
        if (this.relProductAttributeValues == null) {
            this.relProductAttributeValues = new HashSet<>();
        }
        this.relProductAttributeValues.addAll(relProductAttributeValues);
    }

    public void removeAttributeValues(Set<RelProductAttributeValue> relProductAttributeValuesToRemove) {
        if (this.relProductAttributeValues == null) {
            return;
        }
        this.relProductAttributeValues.removeAll(relProductAttributeValuesToRemove);

    }

    public void addProductVariants(Set<ProductVariant> productVariants) {
        if (this.productVariants == null) {
            this.productVariants = new HashSet<>();
        }
        this.productVariants.addAll(productVariants);
    }


    public void removeProductImages(Set<ProductImage> productImagesToRemove) {
        if (this.productImages == null) {
            return;
        }
        this.productImages.removeAll(productImagesToRemove);
    }

}

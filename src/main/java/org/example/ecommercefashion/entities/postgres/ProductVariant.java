package org.example.ecommercefashion.entities.postgres;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.example.ecommercefashion.dtos.request.ProductRequest;
import org.example.ecommercefashion.enums.ProductState;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "product_variants")
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProductVariant extends BaseEntity {

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private ProductState state = ProductState.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonBackReference
    private Product product;
    @Column(name = "product_id")
    private Long productId;
    @Type(type = "jsonb")
    @Column(name = "variants", columnDefinition = "jsonb")
    private Set<Variant> variants;

    public static ProductVariant fromRequest(Set<Variant> variantList, Product product, ProductRequest request) {
        return ProductVariant.builder()
                .productId(product.getId())
                .product(product)
                .quantity(request.getQuantity())
                .state(request.getState())
                .variants(variantList)
                .build();
    }

    @JsonBackReference
    public Product getProduct() {
        return this.product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVariant that = (ProductVariant) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(variants, that.variants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, variants);
    }


}

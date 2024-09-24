package org.example.ecommercefashion.entities.postgres;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "cart_items")
@Entity
public class CartItem extends BaseEntity {

    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;


    @OneToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "product_variant_id", insertable = false, updatable = false)
    private ProductVariant productVariant;

    @Column(name = "product_variant_id")
    private Long productVariantId;

    @Column(name = "quantity")
    private Integer quantity;

    public static CartItem fromRequest(Cart cart, Product product, ProductVariant productVariant, Integer quantity) {
        BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        return CartItem.builder()
                .cart(cart)
                .cartId(cart.getId())
                .product(product)
                .productId(product.getId())
                .productVariant(productVariant)
                .productVariantId(productVariant != null ? productVariant.getId() : null)
                .quantity(quantity)
                .price(price)
                .build();

    }
}

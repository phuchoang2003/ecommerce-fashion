package org.example.ecommercefashion.entities.postgres;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderItem extends BaseEntity {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_variant_id", insertable = false, updatable = false)
    private ProductVariant productVariant;

    @Column(name = "product_variant_id")
    private Long productVariantId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderDetail orderDetail;

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total")
    private BigDecimal total;

    public static OrderItem fromRequest(Integer quantity, OrderDetail orderDetail, Product product, ProductVariant productVariant) {
        return OrderItem.builder()
                .productId(product.getId())
                .product(product)
                .productVariant(productVariant)
                .productVariantId(productVariant != null ? productVariant.getId() : null)
                .price(product.getPrice())
                .total(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .quantity(quantity)
                .orderId(orderDetail.getId())
                .orderDetail(orderDetail)
                .build();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (((OrderItem) o).getId() == null) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(this.getId(), orderItem.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}

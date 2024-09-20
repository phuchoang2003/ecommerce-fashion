package org.example.ecommercefashion.entities.postgres;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_images")
@Entity
public class ProductImage implements Serializable {
    @Column(name = "image_id")
    @Id
    private Long imageId;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private Image image;


    public ProductImage(Long imageId, Long productId) {
        this.imageId = imageId;
        this.productId = productId;
    }
}

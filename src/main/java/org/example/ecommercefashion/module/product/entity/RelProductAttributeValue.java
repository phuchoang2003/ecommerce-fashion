package org.example.ecommercefashion.module.product.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rel_product_attribute_value")
@Getter
@Setter
public class RelProductAttributeValue implements Serializable {

    @EmbeddedId
    private ProductAttributeValueId productAttributeValueId;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("attributeValueId")
    @JoinColumn(name = "attribute_value_id")
    private AttributeValue attributeValue;


    public RelProductAttributeValue(Product product, AttributeValue attributeValue) {
        this.product = product;
        this.attributeValue = attributeValue;
        this.productAttributeValueId = new ProductAttributeValueId(product.getId(), attributeValue.getId());
    }

    public RelProductAttributeValue() {

    }
}

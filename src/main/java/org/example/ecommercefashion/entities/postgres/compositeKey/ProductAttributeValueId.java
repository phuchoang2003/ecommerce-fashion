package org.example.ecommercefashion.entities.postgres.compositeKey;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductAttributeValueId implements Serializable {
    private Long productId;
    private Long attributeValueId;

    public ProductAttributeValueId() {
    }

    public ProductAttributeValueId(Long productId, Long attributeValueId) {
        this.productId = productId;
        this.attributeValueId = attributeValueId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAttributeValueId() {
        return attributeValueId;
    }

    public void setAttributeValueId(Long attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttributeValueId that = (ProductAttributeValueId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(attributeValueId, that.attributeValueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, attributeValueId);
    }
}

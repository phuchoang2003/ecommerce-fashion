package org.example.ecommercefashion.entities.postgres.compositeKey;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CategoryAttributeId implements Serializable {

    private Long categoryId;
    private Long attributeId;

    // Default constructor
    public CategoryAttributeId() {
    }

    public CategoryAttributeId(Long categoryId, Long attributeId) {
        this.categoryId = categoryId;
        this.attributeId = attributeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CategoryAttributeId that = (CategoryAttributeId) obj;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(attributeId, that.attributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, attributeId);
    }
}

package org.example.ecommercefashion.entities.postgres;


import org.example.ecommercefashion.entities.postgres.compositeKey.CategoryAttributeId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rel_category_attribute")
public class RelCategoryAttribute implements Serializable {

    @EmbeddedId
    private CategoryAttributeId categoryAttributeId;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    public RelCategoryAttribute() {
    }

    public RelCategoryAttribute(Attribute attribute, Category category) {
        this.attribute = attribute;
        this.category = category;
        this.categoryAttributeId = new CategoryAttributeId(attribute.getId(), category.getId());
    }
}
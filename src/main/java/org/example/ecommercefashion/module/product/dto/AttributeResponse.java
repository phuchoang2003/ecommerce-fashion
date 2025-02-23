package org.example.ecommercefashion.module.product.dto;

import lombok.Builder;
import org.example.ecommercefashion.module.product.entity.Attribute;
import org.example.ecommercefashion.module.product.entity.AttributeValue;

import java.util.Set;

@Builder
public record AttributeResponse(Long attributeId, String key, String displayKey, Boolean isMandatory,
                                Set<AttributeValue> attributeValues) {

    public static AttributeResponse fromModel(Attribute attribute) {
        return AttributeResponse.builder()
                .attributeId(attribute.getId())
                .key(attribute.getKeyAttribute())
                .displayKey(attribute.getDisplayKey())
                .isMandatory(attribute.getIsMandatory())
                .attributeValues(attribute.getAttributeValues())
                .build();
    }
}

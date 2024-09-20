package org.example.ecommercefashion.dtos.response;

import lombok.Builder;
import org.example.ecommercefashion.entities.postgres.Attribute;
import org.example.ecommercefashion.entities.postgres.AttributeValue;

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

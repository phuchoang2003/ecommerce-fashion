package org.example.ecommercefashion.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateAttributeRequest {
    @NotNull(message = "You should set the attribute must be mandatory or not")
    private Boolean isMandatory;

    @NotEmpty(message = "Attribute values cannot be empty")
    @NotNull(message = "Attribute values cannot be null")
    private Set<@Valid AttributeValueRequest> newAttributeValues;
}

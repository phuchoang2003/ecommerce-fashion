package org.example.ecommercefashion.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AttributeRequest {
    @NotBlank(message = "Attribute's Key cannot be blank")
    @Length(min = 1, max = 255, message = "The length must be in range 1 to 255")
    private String key;

    @NotBlank(message = "Attribute's display key cannot be blank")
    @Length(min = 1, max = 255, message = "The length must be in range 1 to 255")
    private String displayKey;

    @NotNull(message = "You should set the attribute must be mandatory or not")
    private Boolean isMandatory;

    @NotEmpty(message = "Attribute values cannot be empty")
    @NotNull(message = "Attribute values cannot be null")
    private Set<@Valid AttributeValueRequest> attributeValues;

}

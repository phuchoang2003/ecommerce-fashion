package org.example.ecommercefashion.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AttributeValueRequest {

    @NotBlank(message = "Attribute value cannot be blank")
    @Length(min = 1, max = 255, message = "The length must be in range 1 to 255")
    private String value;

    @NotBlank(message = "Attribute display value cannot be blank")
    @Length(min = 1, max = 255, message = "The length must be in range 1 to 255")
    private String displayValue;

    @Override
    public int hashCode() {
        return Objects.hash(value, displayValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AttributeValueRequest that = (AttributeValueRequest) obj;
        return Objects.equals(value, that.value) &&
                Objects.equals(displayValue, that.displayValue);
    }
}

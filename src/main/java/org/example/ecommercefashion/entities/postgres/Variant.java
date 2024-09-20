package org.example.ecommercefashion.entities.postgres;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.beans.Transient;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variant implements Serializable {


    @Size(min = 1, max = 50, message = "Name must be less than 50 characters")
    @JsonProperty("key")
    private String name;

    @Size(min = 1, max = 50, message = "Name must be less than 50 characters")
    @Pattern(regexp = "^[^|]+(\\|[^|]+)*$", message = "Values must be separated by '|' and must not start or end with '|'")
    @JsonProperty("value")
    private String value;


    @AssertTrue(message = "If name is provided, value must not be null or empty, and vice versa")
    @Transient
    public boolean isValidVariant() {
        if ((name != null && !name.trim().isEmpty()) || (value != null && !value.trim().isEmpty())) {
            return name != null && !name.trim().isEmpty() && value != null && !value.trim().isEmpty();
        }
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variant variant = (Variant) o;
        return Objects.equals(name != null ? name.toLowerCase() : null,
                variant.name != null ? variant.name.toLowerCase() : null) &&
                Objects.equals(value != null ? value.toLowerCase() : null,
                        variant.value != null ? variant.value.toLowerCase() : null);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name != null ? name.toLowerCase() : null,
                value != null ? value.toLowerCase() : null);
    }

}

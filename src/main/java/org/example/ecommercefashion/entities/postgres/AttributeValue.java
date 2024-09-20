package org.example.ecommercefashion.entities.postgres;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.example.ecommercefashion.dtos.request.AttributeValueRequest;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "attribute_values")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne(cascade = {CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", insertable = false, updatable = false)
    @JsonBackReference
    private Attribute attribute;

    @Column(name = "attribute_id")
    private Long attributeId;

    @Column(name = "display_value")
    private String displayValue;

    public static AttributeValue fromRequest(AttributeValueRequest request, Attribute attribute) {
        return AttributeValue.builder()
                .value(request.getValue())
                .displayValue(request.getDisplayValue())
                .attribute(attribute)
                .attributeId(attribute.getId())
                .build();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeValue that = (AttributeValue) o;
        return Objects.equals(attributeId, that.attributeId) &&
                Objects.equals(normalize(value), normalize(that.value)) &&
                Objects.equals(normalize(displayValue), normalize(that.displayValue));
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeId,
                normalize(value),
                normalize(displayValue));
    }

    private String normalize(String value) {
        return value != null ? value.trim().toLowerCase() : null;
    }

}

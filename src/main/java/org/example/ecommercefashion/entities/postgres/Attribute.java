package org.example.ecommercefashion.entities.postgres;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.example.ecommercefashion.dtos.request.AttributeRequest;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "attributes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<RelCategoryAttribute> relCategoryAttributes;

    @Column(name = "key_attribute", unique = true, updatable = false)
    private String keyAttribute;

    @Column(name = "is_mandatory", updatable = false)
    private Boolean isMandatory;

    @Column(name = "display_key", unique = true, updatable = false)
    private String displayKey;


    @OneToMany(mappedBy = "attributeId",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<AttributeValue> attributeValues;


    public static Attribute fromRequest(AttributeRequest request) {
        return Attribute.builder()
                .keyAttribute(request.getKey())
                .displayKey(request.getDisplayKey())
                .isMandatory(request.getIsMandatory())
                .build();
    }

    public Set<AttributeValue> getAttributeValues() {
        return attributeValues == null ? Collections.emptySet() : attributeValues;
    }

    public void addAttributeValues(Set<AttributeValue> attributeValues) {
        if (this.attributeValues == null) this.attributeValues = new HashSet<>();
        this.attributeValues.addAll(attributeValues);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return Objects.equals(id, attribute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

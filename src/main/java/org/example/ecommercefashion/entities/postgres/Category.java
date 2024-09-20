package org.example.ecommercefashion.entities.postgres;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.example.ecommercefashion.dtos.request.CategoryRequest;
import org.example.ecommercefashion.utils.SlugUtils;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "categories")
@TypeDef(
        name = "long-array",
        typeClass = ListArrayType.class
)
@Entity
public class Category extends BaseEntity {
    @Column(name = "level")
    private Integer level;

    @Column(name = "parent_id")
    private Long parentId;

    @OneToMany(mappedBy = "parent",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 5)
    private Set<Category> subCategories;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @JsonBackReference
    private Category parent;

    @Column(name = "slug")
    private String slug;

    @Column(name = "support_size_chart_ids", columnDefinition = "bigint[]")
    @Type(type = "long-array")
    private List<Long> supportSizeChartIds;


    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RelCategoryAttribute> relCategoryAttributes;


    @Column(name = "name")
    private String name;

    @Column(name = "left_value")
    private Long leftValue;

    @Column(name = "right_value")
    private Long rightValue;

    public static Category fromRequest(CategoryRequest request, Category parent, int level, long maxValue) {
        return Category.builder()
                .level(level)
                .rightValue(maxValue + 1)
                .leftValue(maxValue)
                .name(request.getName())
                .slug(SlugUtils.generateSlug(request.getName()))
                .supportSizeChartIds(new ArrayList<>(request.getSupportSizeChartIds()))
                .parentId(request.getParentId())
                .parent(parent)
                .build();
    }

    public Set<Category> getSubCategories() {
        if (subCategories == null) return new HashSet<>();
        return subCategories;
    }

}

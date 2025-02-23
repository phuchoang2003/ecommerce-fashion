package org.example.ecommercefashion.common.storage.entity;


import lombok.*;
import org.example.ecommercefashion.common.core.entity.BaseEntity;
import org.example.ecommercefashion.common.storage.enums.TypeImage;
import org.example.ecommercefashion.module.product.entity.ProductImage;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "images")
public class Image extends BaseEntity {

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeImage type;

    @Column(name = "size")
    private Long size;

    @Column(name = "title")
    private String title;

    @OneToOne(mappedBy = "image", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private ProductImage productImage;

}
 
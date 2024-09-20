package org.example.ecommercefashion.entities.postgres;


import lombok.*;
import org.example.ecommercefashion.enums.TypeImage;
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
    private ProductImage productImage;

}
 
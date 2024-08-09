package org.example.ecommercefashion.entities;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.ecommercefashion.enums.TypeImage;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@Table(name = "images" )
public class Image extends BaseEntity{

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeImage type;

    @Column(name = "size")
    private Long size;

    @Column(name = "title")
    private String title;


}
 
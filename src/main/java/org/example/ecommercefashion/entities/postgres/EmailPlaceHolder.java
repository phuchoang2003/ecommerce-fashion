package org.example.ecommercefashion.entities.postgres;


import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "email_place_holders")
@Entity
public class EmailPlaceHolder extends BaseEntity {

    @Column(name = "place_holder_name")
    private String placeHolderName;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "data_type")
    private String dataType;


}

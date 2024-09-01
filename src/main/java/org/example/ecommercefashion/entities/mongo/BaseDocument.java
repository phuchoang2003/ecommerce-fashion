package org.example.ecommercefashion.entities.mongo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDocument {
    @Id
    private String id;

    @CreatedDate
    @Field("created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Date lastModifiedAt;

    @Field("deleted")
    private Boolean isDeleted;

    @Field("deleted_at")
    private Date deletedAt;
}

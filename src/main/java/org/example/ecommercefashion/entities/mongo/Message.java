package org.example.ecommercefashion.entities.mongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "messages")
public class Message extends BaseDocument {
    @Field("content")
    private String content;

    @Field("sender_id")
    private Long senderId;

    @Field("receiver_id")
    private Long receiverId;


}

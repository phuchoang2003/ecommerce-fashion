package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.mongo.Message;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Message sendMessage(Message request);

    ResponsePage<Message,Message> getMessages(Long senderId, Long receiverId, Pageable pageable);
}

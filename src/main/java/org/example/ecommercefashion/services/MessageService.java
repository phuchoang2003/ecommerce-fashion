package org.example.ecommercefashion.services;

import org.example.ecommercefashion.entities.mongo.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Message sendMessage(Message request);

    Page<Message> getMessages(Long senderId, Long receiverId, Pageable pageable);
}

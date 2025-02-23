package org.example.ecommercefashion.module.chat.service;

import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.chat.document.Message;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Message sendMessage(Message request);

    ResponsePage<Message, Message> getMessages(Long senderId, Long receiverId, Pageable pageable);
}

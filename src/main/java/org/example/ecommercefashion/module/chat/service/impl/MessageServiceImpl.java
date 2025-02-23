package org.example.ecommercefashion.module.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.chat.document.Message;
import org.example.ecommercefashion.module.chat.repository.MessageMongoRepository;
import org.example.ecommercefashion.module.chat.service.MessageService;
import org.example.ecommercefashion.module.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageMongoRepository messageMongoRepository;

    private final UserService userService;


    @Override
    public Message sendMessage(Message request) {
        Set<Long> userIds = new HashSet<>();
        userIds.add(request.getReceiverId());
        userService.checkUsersExists(userIds);
        return messageMongoRepository.save(request);
    }


    public ResponsePage<Message, Message> getMessages(Long senderId, Long receiverId, Pageable pageable) {
        return new ResponsePage<>(messageMongoRepository.findAllMessageLatest(senderId, receiverId, pageable));

    }
}

package org.example.ecommercefashion.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.mongo.Message;
import org.example.ecommercefashion.repositories.mongo.MessageMongoRepository;
import org.example.ecommercefashion.services.MessageService;
import org.example.ecommercefashion.services.UserService;
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

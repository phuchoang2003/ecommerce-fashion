package org.example.ecommercefashion.module.chat.port;


import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.chat.document.Message;
import org.example.ecommercefashion.module.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/messages")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<ResponsePage<Message, Message>> getMessages(Pageable pageable, Long receiverId) {
        return ResponseEntity.ok(messageService.getMessages(1L, receiverId, pageable));
    }
}

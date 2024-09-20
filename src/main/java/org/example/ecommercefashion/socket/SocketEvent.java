package org.example.ecommercefashion.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.entities.mongo.Message;
import org.example.ecommercefashion.enums.TokenType;
import org.example.ecommercefashion.security.JwtUtils;
import org.example.ecommercefashion.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketEvent {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtUtils jwtUtils;

    private Long getSenderId(SocketIOClient client) {
        String token = client.getHandshakeData().getHttpHeaders().get("Authorization");
        return Long.parseLong(jwtUtils.extractUserId(token, TokenType.ACCESS));
    }


    public DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            try {
                data.setSenderId(getSenderId(senderClient));
                messageService.sendMessage(data);
                senderClient.getNamespace().getBroadcastOperations().sendEvent(SocketListener.Event.GET_MESSAGE.val(), data);
            } catch (Exception e) {
                log.info("Error: {}", e.getMessage());
                onError(senderClient, e);
            }
        };
    }

    public void onError(SocketIOClient client, Exception errorMessage) {
        // Emit a specific error message to the client
        client.getNamespace().getBroadcastOperations().sendEvent(SocketListener.Event.ERROR.val(), errorMessage.getMessage());
    }

    public ConnectListener onConnected() {
        return (client) -> {
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };

    }

    public DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

}

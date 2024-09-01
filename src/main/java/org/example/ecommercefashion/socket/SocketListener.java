package org.example.ecommercefashion.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.entities.mongo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketListener {

    private final SocketIOServer server;

    private final SocketEvent event;


    @Autowired
    public SocketListener(SocketIOServer server, SocketEvent event) {
        this.server = server;
        this.event = event;
        server.addConnectListener(event.onConnected());
        server.addDisconnectListener(event.onDisconnected());
        server.addEventListener(Event.SEND_MESSAGE.val(), Message.class, event.onChatReceived());
    }

    enum Event {
        SEND_MESSAGE("send_message"),
        GET_MESSAGE("get_message"),
        ERROR("error");

        private String val;

        Event(String val) {
            this.val = val;
        }

        public String val() {
            return val;
        }
    }

}
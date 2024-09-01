package org.example.ecommercefashion.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Slf4j
public class SocketIOServerRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public SocketIOServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }

    @PreDestroy
    public void stop() {
        this.server.stop();
        log.info("Socket.IO server stopped");
    }

}
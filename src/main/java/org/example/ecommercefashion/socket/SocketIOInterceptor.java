//package org.example.ecommercefashion.socket;
//
//import com.corundumstudio.socketio.HandshakeData;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.listener.ConnectListener;
//import com.longnh.exceptions.ExceptionHandle;
//import lombok.extern.slf4j.Slf4j;
//import org.example.ecommercefashion.exceptions.ErrorMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@Slf4j
//public class SocketIOInterceptor implements ConnectListener {
//
//    private final JwtService jwtService;
//
//
//    private final UserDetailsService userDetailsService;
//
//
//    private final SocketEvent event;
//
//    @Autowired
//    public SocketIOInterceptor(JwtService jwtService, UserDetailsService userDetailsService, SocketEvent event) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//        this.event = event;
//    }
//
//
//    @Override
//    public void onConnect(SocketIOClient client) {
//        HandshakeData handshakeData = client.getHandshakeData();
//        String token = handshakeData.getHttpHeaders().get("Authorization");
//
//        try {
//            if (token != null) {
//                String email = jwtService.extractUserName(token, jwtService.getJwtKey());
//                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
//                    if (jwtService.isTokenValid(token, userDetails, jwtService.getJwtKey())) {
//                        return;
//                    }
//                }
//            }
//        } catch (Exception ignore) {
//
//        }
//        ExceptionHandle error = new ExceptionHandle(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED.val());
//
//        event.onError(client, error);
//        client.disconnect();
//    }
//
//}

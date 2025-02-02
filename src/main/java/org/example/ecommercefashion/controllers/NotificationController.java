package org.example.ecommercefashion.controllers;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final FirebaseMessaging firebaseMessaging;

    // save token (token device)
    // history notification
    // delete token
    // api send notify
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification() {
        try {

            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setBody("Hello, this is a notification!")
                            .setTitle("Notification Title")
                            .build())
                    .setTopic("test")
                    .build();

            // Thử gửi
            String response = firebaseMessaging.send(message);
            return ResponseEntity.ok("Notification sent successfully! Message ID: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send notification: " + e.getMessage());
        }
    }
}




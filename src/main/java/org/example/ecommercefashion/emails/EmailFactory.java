package org.example.ecommercefashion.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class EmailFactory {

    @Autowired
    List<EmailSender> emailSenders;


    @Async(value = "ioTaskExecutor")
    public void sendEmail(String to, String subject, String body) {
        for (EmailSender emailSender : emailSenders) {
            try {
                emailSender.sendEmailApi(to, subject, body, "test");
                return;
            } catch (Exception e) {
                System.err.println("Email service failed: " + emailSender.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

}

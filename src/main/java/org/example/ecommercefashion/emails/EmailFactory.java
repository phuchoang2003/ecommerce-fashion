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
    public void sendEmail(Long idTemplate, String sendTo, String sendFrom, Object object) {
        for (EmailSender emailSender : emailSenders) {
            try {
                emailSender.sendEmailApi(idTemplate, sendTo, sendFrom, object);
                return;
            } catch (Exception e) {
                System.err.println("Email service failed: " + emailSender.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

}

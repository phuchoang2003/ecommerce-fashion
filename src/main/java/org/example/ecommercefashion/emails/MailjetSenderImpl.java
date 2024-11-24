package org.example.ecommercefashion.emails;


import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.entities.postgres.Email;
import org.example.ecommercefashion.services.EmailService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@Service
@Slf4j
public class MailjetSenderImpl implements EmailSender {
    private static final String NAME_COMPANY = "Fashion Store";
    private final MailjetClient mailjetClient;
    private final EmailService emailService;

    @Override
    public CompletableFuture<Void> sendEmailApi(Long idTemplate, String sendTo, String sendFrom, Object object) {
        Email email = emailService.createEmail(idTemplate, sendTo, sendFrom, object);

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", email.getSendFrom()))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", email.getSendTo())))
                                .put(Emailv31.Message.SUBJECT, email.getSubject())
                                .put(Emailv31.Message.HTMLPART, email.getBody())));


        return CompletableFuture.supplyAsync(() -> {
            try {
                MailjetResponse response = mailjetClient.post(request);
                if (response.getStatus() == 200) {
                    System.out.println("Email sent successfully.");
                    return null;
                } else {
                    System.out.println("Failed to send email: " + response.getData());
                    throw new RuntimeException("Failed to send email.");
                }
            } catch (Exception e) {
                System.out.println("Error while sending email: " + e.getMessage());
                throw new RuntimeException("Error while sending email.", e);
            }
        });
    }

    // handle failure


}

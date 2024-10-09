package org.example.ecommercefashion.emails;


import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@Service
@Slf4j
public class MailjetSenderImpl implements EmailSender {
    private final MailjetClient mailjetClient;


    @Override
    public CompletableFuture<Void> sendEmailApi(String sendFrom, String sendTo, String subject, String content) {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "pilot@mailjet.com")
                                        .put("Name", "Mailjet Pilot"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", "passenger1@mailjet.com")
                                                .put("Name", "passenger 1")))
                                .put(Emailv31.Message.SUBJECT, "Your email flight plan!")
                                .put(Emailv31.Message.TEXTPART, "Dear passenger 1, welcome to Mailjet! May the delivery force be with you!")
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href=\"https://www.mailjet.com/\">Mailjet</a>!</h3><br />May the delivery force be with you!")));

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
}

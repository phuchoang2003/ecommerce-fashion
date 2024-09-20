package org.example.ecommercefashion.emails;


import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${mailjet.api.key}")
    private String mailjetApiKey;


    @Value(("${mailjet.api.secret}"))
    private String mailJetSecretKey;

    @Bean
    MailjetClient mailjetClient() {
        ClientOptions options = ClientOptions.builder()
                .apiKey(mailjetApiKey)
                .apiSecretKey(mailJetSecretKey)
                .build();
        return new MailjetClient(options);
    }
}

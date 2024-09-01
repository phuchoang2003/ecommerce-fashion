package org.example.ecommercefashion.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongoAuditing
public class MongoConfig {


    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(uri);
    }


    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), extractDatabaseName(uri));
    }

    private String extractDatabaseName(String uri) {
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

}

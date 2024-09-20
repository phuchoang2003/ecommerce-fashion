package org.example.ecommercefashion.emails;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {
    CompletableFuture<Void> sendEmailApi(String sendFrom, String sendTo, String subject, String content);

}

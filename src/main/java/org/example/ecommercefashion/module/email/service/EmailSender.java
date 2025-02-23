package org.example.ecommercefashion.module.email.service;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {
    CompletableFuture<Void> sendEmailApi(Long idTemplate, String sendTo, String sendFrom, Object object);

}

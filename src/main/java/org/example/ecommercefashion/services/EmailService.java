package org.example.ecommercefashion.services;

import org.example.ecommercefashion.entities.postgres.Email;

public interface EmailService {
    Email createEmail(Long idTemplate, String sendTo, String sendFrom, Object object);
}

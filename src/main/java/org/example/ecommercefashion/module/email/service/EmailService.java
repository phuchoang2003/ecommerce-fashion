package org.example.ecommercefashion.module.email.service;

import org.example.ecommercefashion.module.email.entity.Email;

public interface EmailService {
    Email createEmail(Long idTemplate, String sendTo, String sendFrom, Object object);
}

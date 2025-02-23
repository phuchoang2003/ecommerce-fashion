package org.example.ecommercefashion.module.email.repository;


import org.example.ecommercefashion.module.email.enums.EmailTemplateEnums;
import org.example.ecommercefashion.module.email.entity.EmailTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    List<EmailTemplate> findByTemplateName(EmailTemplateEnums templateName);

    Optional<EmailTemplate> findByTemplateNameAndIsActive(EmailTemplateEnums templateName, boolean isActive);

    boolean existsByTemplateName(EmailTemplateEnums templateName);

    Page<EmailTemplate> findAll(Pageable pageable);
}

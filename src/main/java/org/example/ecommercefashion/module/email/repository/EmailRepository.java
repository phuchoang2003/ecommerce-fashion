package org.example.ecommercefashion.module.email.repository;

import org.example.ecommercefashion.module.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
}

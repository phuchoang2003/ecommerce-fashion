package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.ResetPasswordToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetTokenPasswordRepository extends JpaRepository<ResetPasswordToken, Long> {


    @EntityGraph(attributePaths = "user")
    @Query("SELECT r FROM ResetPasswordToken r " +
            "WHERE r.token = :token " +
            "AND r.isUsed = false " +
            "AND r.expirationAt > NOW()")
    Optional<ResetPasswordToken> findToken(String token);
}

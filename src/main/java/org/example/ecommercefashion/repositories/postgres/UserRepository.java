package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);


    @Query("SELECT u.id FROM User u WHERE u.id IN :userIds")
    Set<Long> findByIdIn(@Param("userIds") Set<Long> userIds);


    @Query("SELECT u FROM User u " +
            "LEFT JOIN ResetPasswordToken r ON u.id = r.userId " +
            "WHERE r.token = :token")
    Optional<User> findByResetPasswordToken(String token);
}

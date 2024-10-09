package org.example.ecommercefashion.repositories.postgres;

import org.example.ecommercefashion.entities.postgres.JwtToken;
import org.example.ecommercefashion.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<JwtToken, Long> {
    @Query("SELECT rt FROM JwtToken rt WHERE rt.hashToken = :hashToken " +
            "AND rt.tokenType = :type " +
            "AND rt.expirationAt > CURRENT_TIMESTAMP")
    List<JwtToken> findByHashToken(@Param("hashToken") String hashToken, @Param("type") TokenType type);

    @Query(value = "SELECT nextval('jwt_token_id_seq')", nativeQuery = true)
    Long getNextSeq();


}

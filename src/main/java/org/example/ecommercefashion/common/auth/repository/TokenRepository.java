package org.example.ecommercefashion.common.auth.repository;

import org.example.ecommercefashion.common.auth.entity.JwtToken;
import org.example.ecommercefashion.common.auth.enums.TokenType;
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

package org.example.ecommercefashion.entities.postgres;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.enums.TokenType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "jwt_token")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JwtToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "hash_token")
    private String hashToken;

    @Column(name = "expiration_at")
    private Timestamp expirationAt;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;


    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "token_type")
    private TokenType tokenType;

    @Column(name = "device")
    private String device;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "browser")
    private String browser;

    @Column(name = "ip")
    private String ip;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reference_token_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private JwtToken referenceToken;
}

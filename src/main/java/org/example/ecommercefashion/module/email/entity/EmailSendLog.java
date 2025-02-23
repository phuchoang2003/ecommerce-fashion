package org.example.ecommercefashion.module.email.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.example.ecommercefashion.module.email.enums.EmailStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "email_send_logs")
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class EmailSendLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "retry_count", columnDefinition = "int default 0")
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "max_attempt", columnDefinition = "int default 3")
    @Builder.Default
    private Integer maxAttempt = 3;

    @Column(name = "error_message")
    private String errorMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_id", nullable = false)
    @JsonIgnore
    private Email email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp deletedAt;

    @Column(name = "send_at")
    @Type(type = "jsonb")
    private List<String> sendAt;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private Timestamp createdAt;
}

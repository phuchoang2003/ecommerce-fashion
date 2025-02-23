package org.example.ecommercefashion.entities.postgres;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.ecommercefashion.dtos.request.EmailTemplateRequest;
import org.example.ecommercefashion.enums.EmailTemplateEnums;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@Table(name = "email_template")
@Entity
public class EmailTemplate {

    @Column(name = "template_name")
    @Enumerated(EnumType.STRING)
    private EmailTemplateEnums templateName;

    @Column(name = "description")
    private String description;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp deletedAt;

    @Column(name = "version", columnDefinition = "int default 0")
    @Builder.Default
    private Integer version = 0;


    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = false;

    @Type(type = "jsonb")
    @Column(name = "variables", columnDefinition = "jsonb")
    private List<String> variables;


    @OneToMany(mappedBy = "emailTemplate", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Email> emails;


    public static EmailTemplate fromRequest(EmailTemplateRequest request) {
        return EmailTemplate.builder()
                .templateName(request.getTemplateName())
                .description(request.getDescription())
                .subject(request.getSubject())
                .body(request.getBody())
                .variables(new ArrayList<>(request.getVariables()))
                .version(0)
                .deleted(false)
                .isActive(false)
                .build();
    }


}

package org.example.ecommercefashion.entities.postgres;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommercefashion.enums.GenderEnum;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
public class User extends BaseEntity implements Serializable {

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(name = "password", columnDefinition = "TEXT")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "birth")
    private Date birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;

    @Column(columnDefinition = "TEXT", name = "avatar")
    private String avatar;

    @Column(name = "id_google_account")
    private String googleAccountId;

    @Column(name = "id_facebook_account")
    private String facebookAccountId;

    @Column(nullable = false, name = "is_admin")
    private Boolean isAdmin = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    @JsonIgnore
    private Set<Role> roles;

}

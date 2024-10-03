package com.SportsLink.userAccount.authentication;


import com.SportsLink.loginAudit.LoginAuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users", indexes = {
        @Index(name = "idx_phone_number",columnList = "phone_number",unique = true)
})

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    @NotNull(message = "user phone cannot be null")
    @Size(min = 8, max = 15)
    @Column(name = "phone_number", length = 15, nullable = false)
    private String phone_number;
    @Email
    @Column(name = "email", length = 120, nullable = true)
    private String email;
    @NotNull(message = "Name cannot be null")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    @Column(name = "name",length = 100, nullable = false)
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private RolesEnum role;
    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String password_hash;
    private int failed_attempts = 0;
    private boolean is_blocked = false;
    @Column(name = "blocked_until", nullable = true)
    private Timestamp blocked_until;
    private boolean is_verified = false;
    private boolean is_activated = true;
    private Timestamp created_at;
    private Timestamp last_login;
    @JsonIgnore
    @OneToMany(mappedBy = "user_id")
    private List<LoginAuditModel> loginAudits;

}

package com.SportsLink.userAccount.authentication;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user") //
// ,indexes = {@Index(name = "idx_event_status",columnList = "status")})

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String phone_number;
    private String email;
    private String name;
    private RolesEnum role;
    private String password_hash;
    private int failed_attempts;
    private boolean is_blocked;
    private Timestamp blocked_until;
    private boolean is_verified;
    private boolean is_activated;
    private Timestamp created_at;
    private Timestamp last_login;

}

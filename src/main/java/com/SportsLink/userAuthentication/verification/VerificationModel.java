package com.SportsLink.userAuthentication.verification;


import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_verification", indexes = {
        @Index(name = "idx_user_verify_code",columnList = "user_id, verification_code",unique = true)

})

public class VerificationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int verification_id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user_id;
    @Column(name = "verification_code", length = 10, nullable = false)
    private String verification_code;
    @Column(name = "verification_channel", length = 10, nullable = true)
    private String verification_channel;
    private Timestamp expires_at;
    private boolean is_verified;
    private Timestamp created_at;
    private Timestamp last_created;
    private int attempt_count;
}

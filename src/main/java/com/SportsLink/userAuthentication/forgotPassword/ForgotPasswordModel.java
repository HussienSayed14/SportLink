package com.SportsLink.userAuthentication.forgotPassword;

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
@Table(name = "forgot_password", indexes = {
        @Index(name = "idx_userId",columnList = "user_id",unique = true)
})
public class ForgotPasswordModel {

    @Id
    @Column(name = "uuid")
    private String UUID;
    @OneToOne
    @JoinColumn(name = "user_id")
    UserModel user_id;
    private Timestamp created_at;
    private boolean is_used;

}

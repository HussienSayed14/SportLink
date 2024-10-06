package com.SportsLink.loginAudit;

import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "login_audit", indexes = {
        @Index(name = "idx_login_date",columnList = "login_date")
})
public class LoginAuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int login_audit_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user_id;
    private String session_id;
    @Column(length = 40)
    private String  ip_address;
    @Column(nullable = false)
    private Date login_date;
    private Time login_time;
    private Timestamp logout_time;
    private boolean is_success;
    private String description;
}

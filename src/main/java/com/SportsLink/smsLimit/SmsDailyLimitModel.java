package com.SportsLink.smsLimit;

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
@Table(name = "sms_daily_limit", indexes = {
        @Index(name = "idx_userId_type",columnList = "user_id,type",unique = true)
})
public class SmsDailyLimitModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int limit_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user_id;
    @Column(length = 30)
    private String type;
    private int user_attempts;
    private int daily_attempts;
    private Timestamp last_created;


}

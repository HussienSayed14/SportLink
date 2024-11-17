package com.SportsLink.followers;


import com.SportsLink.fields.FieldModel;
import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follow")
public class FollowerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private FieldModel field;

    @Column(name = "followed_at", nullable = false)
    private LocalDateTime followedAt;

    @PrePersist
    protected void onCreate() {
        this.followedAt = LocalDateTime.now();
    }
}

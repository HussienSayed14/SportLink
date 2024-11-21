package com.SportsLink.notification;

import com.SportsLink.fields.FieldModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private FieldModel field;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserNotificationModel> userNotifications;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}

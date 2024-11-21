package com.SportsLink.notification;

import com.SportsLink.userAuthentication.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_notification")
public class UserNotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user; // The user receiving the notification

    @ManyToOne
    @JoinColumn(name = "notification_id", nullable = false)
    private NotificationModel notification; // The notification details


    @Column(nullable = false)
    private boolean isRead = false; // Whether the user has read the notification

}

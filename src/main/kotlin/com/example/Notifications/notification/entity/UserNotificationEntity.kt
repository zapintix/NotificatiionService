package com.example.Notifications.notification.entity

import jakarta.persistence.*

@Entity
@Table(name = "user_notifications")
data class UserNotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "notification_id", nullable = false)
    val notificationId: Long,

    @Column(name = "viewed", nullable = false)
    var viewed: Boolean,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean
)

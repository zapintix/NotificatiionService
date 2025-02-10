package com.example.Notifications.notification.repository

import com.example.Notifications.notification.entity.NotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<NotificationEntity, Long> {
    @Query(
        """
    SELECT n
    FROM NotificationEntity n
    JOIN UserNotificationEntity un ON n.id = un.notificationId
    WHERE un.userId = :userId AND un.viewed = false
    """
    )
    fun findAllUnviewed(userId: Long): List<NotificationEntity>
}
package com.example.Notifications.notification.repository

import com.example.Notifications.notification.entity.UserNotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserNotificationRepository:JpaRepository<UserNotificationEntity, Long>{
    @Query(
        """
    SELECT n
    FROM NotificationEntity n
    JOIN UserNotificationEntity un ON n.id = un.notificationId
    WHERE un.userId = :userId AND un.viewed = false
    """
    )
     fun findAllUnviewed(userId: Long): List<UserNotificationEntity>

     @Query(
         """
    SELECT n
    FROM NotificationEntity n
    JOIN UserNotificationEntity un ON n.id = un.notificationId
    WHERE un.viewed = :viewed AND un.isDeleted = :isDeleted
    """
     )
     fun findByViewedAndIsDeleted(viewed: Boolean, isDeleted: Boolean): List<UserNotificationEntity>
    @Query(
        """
    SELECT *
    FROM user_notifications
    WHERE viewed = :viewed AND is_deleted = :isDeleted
    """,
        nativeQuery = true
    )
     fun findByViewedAndIsDeletedForDelete(viewed: Boolean, isDeleted: Boolean): List<UserNotificationEntity>

    @Query(
        """
    SELECT un
    FROM UserNotificationEntity un
    WHERE un.userId = :userId AND un.viewed = false
    """
    )
     fun markAllViewed(userId: Long):List<UserNotificationEntity>
}
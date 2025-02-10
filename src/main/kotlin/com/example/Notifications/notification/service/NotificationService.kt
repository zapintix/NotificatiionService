package com.example.Notifications.notification.service

import com.example.Notifications.notification.data.NotificationRoot
import com.example.Notifications.notification.entity.NotificationEntity
import com.example.Notifications.user.service.UserService
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val userService: UserService,
    private val webSocketNotificationSender: WebSocketNotificationSender,
    private val notificationDataService: NotificationDataService,
) {

    fun sendNotification(notificationRequest: NotificationRoot, tokenUserId: Long? = null): List<NotificationEntity> {
        val notificationType = if (tokenUserId != null) "PERSONAL" else "PUBLIC"
        val notificationEntity = notificationDataService.createAndSaveNotification(notificationRequest, notificationType)

        return if (tokenUserId != null) {
            val userId = userService.getUserIdByTokenId(tokenUserId)
            notificationDataService.linkNotificationToUser(userId!!, notificationEntity.id!!)
            webSocketNotificationSender.sendNotificationToUser(tokenUserId, notificationEntity.toNotificationRoot())
            listOf(notificationEntity)
        } else {
            val allUsers = userService.getAllUsersId()
            allUsers.forEach { userId ->
                notificationDataService.linkNotificationToUser(userId, notificationEntity.id!!)
            }
            webSocketNotificationSender.sendNotificationToAllUsers(notificationEntity.toNotificationRoot())
            listOf(notificationEntity)
        }
    }
}

package com.example.Notifications.notification.service

import com.example.Notifications.notification.data.NotificationRoot
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class WebSocketNotificationSender(
    private val simpMessagingTemplate: SimpMessagingTemplate
) {
    fun sendNotificationToUser(userId: Long, notification: NotificationRoot) {
        println("FFFSending notification to user: $userId") // Логирование отправки
        simpMessagingTemplate.convertAndSendToUser(
            userId.toString(),
            "/notifications",
            notification
        )
        println("Sending notification to /user/$userId/notifications")
        println("Notification content: $notification")

    }
    fun sendNotificationToAllUsers(notification: NotificationRoot) {
        println("FFFSending notification to AllUsers")
        simpMessagingTemplate.convertAndSend(
            "/topic/notifications",
            notification
        )

        println("Sending public notification to /topic/notifications")
        println("Notification content: $notification")
    }
}
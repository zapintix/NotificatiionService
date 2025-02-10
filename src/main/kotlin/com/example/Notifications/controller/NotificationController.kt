package com.example.Notifications.controller

import com.example.Notifications.notification.service.NotificationDataService
import com.example.Notifications.notification.service.WebSocketNotificationSender
import com.example.Notifications.updates.service.UpdateService
import com.example.Notifications.updates.socket.UpdateSocket
import com.example.Notifications.user.service.UserService
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.messaging.simp.stomp.StompHeaderAccessor

@RestController
class NotificationController(
    private val notificationDataService: NotificationDataService,
    private val userService: UserService,
    private val webSocketNotificationSender: WebSocketNotificationSender,
    private val updateService: UpdateService,
    private val updateSocket: UpdateSocket
) {

    @MessageMapping("getNotifications")
    fun getNotifications(
        @Header("Authorization") token: String,
        stompHeaderAccessor: StompHeaderAccessor
    ){
        val clearToken = token.replace("Bearer ", "")
        println(clearToken)
        val sessionAttributes = stompHeaderAccessor.sessionAttributes
        val tokenUserId = sessionAttributes?.get("tokenUserId") as Long
        val userId = tokenUserId.let { userService.getUserByTokenId(it) }?.id
        val notifications = userId?.let { notificationDataService.getAllUnviewedToUser(it) }
        val updates = updateService.getAllUpdates()
        notifications?.forEach{
            webSocketNotificationSender.sendNotificationToUser(tokenUserId, it)
        }
        updates.forEach{
            updateSocket.sendUpdateSocket(it)
        }

    }
}

package com.example.Notifications.updates.socket

import com.example.Notifications.updates.data.UpdateRequest
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class UpdateSocket(
    private val simpMessagingTemplate: SimpMessagingTemplate
){
    fun sendUpdateSocket(update: UpdateRequest){
        simpMessagingTemplate.convertAndSend(
            "/topic/newUpdate",
            update
        )
    }
}
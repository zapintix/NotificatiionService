package com.example.Notifications.notification.data

import com.example.Notifications.notification.data.Notification
import jakarta.validation.Valid
import jakarta.validation.constraints.Size

data class NotificationRoot(
    @field:Valid
        val notification: Notification,

    @field:Size(min = 1, max = 50, message = "Service name size must be between 1 and 50 characters.")
        val serviceName: String
)

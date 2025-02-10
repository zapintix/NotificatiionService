package com.example.Notifications.notification.data

import jakarta.validation.Valid
import jakarta.validation.constraints.Size

data class Notification(
        @field:Size(max = 30, message = "Group size must be between 1 and 30 characters.")
        val group: String,

        @field:Valid
        val body: NotificationBody,

        )
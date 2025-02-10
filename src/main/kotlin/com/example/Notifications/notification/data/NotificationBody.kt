package com.example.Notifications.notification.data

import jakarta.validation.Valid
import jakarta.validation.constraints.Size

data class NotificationBody(
        @field:Size(min = 1, max = 500, message = "Text size must be between 1 and 500 characters.")
        val text: String,

        val status: NotificationStatus,

        val type: NotificationType,

        @field:Valid
        val params: NotificationParams

) {

}

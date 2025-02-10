package com.example.Notifications.notification.data

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class NotificationParams(
        @field:NotNull
        @field:Size(min = 1, max = 50, message = "Link size must be between 1 and 50 characters.")
        val link: String,

        @field:NotNull
        @field:Size(min = 1, max = 50, message = "Request type size must be between 1 and 50 characters.")
        val requestType: String,

        @field:NotNull
        @field:Size(min = 1, max = 200, message = "Request URL size must be between 1 and 200 characters.")
        val requestUrl: String,

        @field:NotNull
        @field:Size(min = 1, max = 1000, message = "Request body size must be between 1 and 1000 characters.")
        val requestBody: String
)

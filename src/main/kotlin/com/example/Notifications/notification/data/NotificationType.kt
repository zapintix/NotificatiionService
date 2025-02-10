package com.example.Notifications.notification.data

import com.fasterxml.jackson.annotation.JsonAlias

enum class NotificationType(val title: String) {
    @JsonAlias("plainText")
    PLAINTEXT("plainText"),

    @JsonAlias("link")
    LINK("link"),

    @JsonAlias("request")
    REQUEST("request");

    companion object {
        fun getByTitle(title: String): NotificationType? {
            return NotificationType.entries.find { it.title == title }!!
        }
    }
}

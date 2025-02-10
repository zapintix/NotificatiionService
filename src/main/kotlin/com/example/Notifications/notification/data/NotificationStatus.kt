package com.example.Notifications.notification.data

import com.fasterxml.jackson.annotation.JsonAlias

enum class NotificationStatus(val title: String) {
    @JsonAlias("error")
    ERROR("error"),

    @JsonAlias("warning")
    WARNING("warning"),

    @JsonAlias("success")
    SUCCESS("success"),

    @JsonAlias("info")
    INFO("info"),

    @JsonAlias("download")
    DOWNLOAD("download");

    companion object {
        fun getByTitle(title: String): NotificationStatus? {
            return entries.find { it.title == title }!!
        }
    }
}

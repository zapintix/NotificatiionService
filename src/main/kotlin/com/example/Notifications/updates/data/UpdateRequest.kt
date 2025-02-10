package com.example.Notifications.updates.data

data class UpdateRequest(
    val title: String,
    val text: String,
    val updateTimeout: Int?,
    val version: String
)
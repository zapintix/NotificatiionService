package com.example.Notifications.updates.entity

import com.example.Notifications.updates.data.UpdateRequest
import jakarta.persistence.*

@Entity
@Table(name = "updates")
class UpdateEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,
    @Column(name = "title", nullable = false)
    val title: String,
    @Column(name = "text", nullable = false)
    val text: String,
    @Column(name = "update_timeout", nullable = false)
    val updateTimeout: Int,
    @Column(name = "version", nullable = false)
    val version: String
){
    fun toUpdateRequest(): UpdateRequest {
        return UpdateRequest(
            title = title,
            text = text,
            updateTimeout = updateTimeout,
            version = version

        )
    }
}
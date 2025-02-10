package com.example.Notifications.notification.entity

import com.example.Notifications.notification.data.NotificationStatus
import com.example.Notifications.notification.data.NotificationType
import com.example.Notifications.notification.data.Notification
import com.example.Notifications.notification.data.NotificationBody
import com.example.Notifications.notification.data.NotificationParams
import com.example.Notifications.notification.data.NotificationRoot
import jakarta.persistence.*


@Entity
@Table(name = "notifications")
class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "notification_group", nullable = false)
    val group: String? = null,

    @Column(name = "text", nullable = false)
    val text: String? = null,

    @Column(name = "status", nullable = false)
    val status: String? = null,

    @Column(name = "type", nullable = false)
    val type: String? = null,

    @Column(name = "link")
    val link: String? = null,

    @Column(name = "request_type")
    val requestType: String? = null,

    @Column(name = "request_url")
    val requestUrl: String? = null,

    @Column(name = "request_body")
    val requestBody: String? = null,

    @Column(name = "service_name", nullable = false)
    val serviceName: String? = null,

    @Column(name = "notification_type", nullable = false)
    val notificationType: String


) {
    fun toNotificationRoot(): NotificationRoot {
        requireNotNull(this.group) { "Group must not be null" }
        requireNotNull(this.text) { "Text must not be null" }
        requireNotNull(this.status) { "Status must not be null" }
        requireNotNull(this.type) { "Type must not be null" }
        requireNotNull(this.link) { "Link must not be null" }
        requireNotNull(this.requestType) { "Request type must not be null" }
        requireNotNull(this.requestUrl) { "Request URL must not be null" }
        requireNotNull(this.requestBody) { "Request body must not be null" }
        requireNotNull(this.serviceName) { "Service name must not be null" }

        return NotificationRoot(
            notification = Notification(
                group = this.group!!,
                body = NotificationBody(
                    text = this.text!!,
                    status = NotificationStatus.getByTitle(this.status!!)!!,
                    type = NotificationType.getByTitle(this.type!!)!!,
                    params = NotificationParams(
                        link = this.link!!,
                        requestType = this.requestType!!,
                        requestUrl = this.requestUrl!!,
                        requestBody = this.requestBody!!
                    )
                ),
            ),
            serviceName = this.serviceName!!
        )
    }

}
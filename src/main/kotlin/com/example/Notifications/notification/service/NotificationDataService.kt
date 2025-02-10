package com.example.Notifications.notification.service

import com.example.Notifications.auth.service.AuthUserService
import com.example.Notifications.notification.data.NotificationRoot
import com.example.Notifications.notification.entity.NotificationEntity
import com.example.Notifications.notification.entity.UserNotificationEntity
import com.example.Notifications.notification.repository.NotificationRepository
import com.example.Notifications.notification.repository.UserNotificationRepository
import com.example.Notifications.user.service.UserService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class NotificationDataService(
    private val userNotificationRepository: UserNotificationRepository,
    private val notificationRepository: NotificationRepository,
    private val userService: UserService,
    private val authUserService: AuthUserService
) {

    fun getAllNotifications(): List<NotificationRoot> {
        return notificationRepository.findAll().map { it.run { toNotificationRoot() } }
    }

    fun getAllNotDeleted(): List<NotificationRoot> {
        println("222")
        return userNotificationRepository.findByViewedAndIsDeleted(false, false)
            .map {userNotification ->
                println(111)
                val notification = notificationRepository.findById(userNotification.notificationId)
                    .orElseThrow{EntityNotFoundException("Уведомления не найдены")}
                notification.toNotificationRoot()
            }
    }

    fun getAllUnviewedToUser(userId: Long): List<NotificationRoot> {
        return notificationRepository.findAllUnviewed(userId)
            .map { notification ->
                println("tytytytytyt")
                notification.toNotificationRoot()
            }
    }

    fun deleteNotification(id: Long) {
        val notification = notificationRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Уведомление с ID $id не найдено") }
        notificationRepository.delete(notification)
    }

    fun markAllViewed(token: String) {
        val cleanToken = token.replace("Bearer ", "")
        val tokenUserId = authUserService.getUserId(cleanToken)
        val userId = tokenUserId?.let { userService.getUserByTokenId(it)?.id }
        if (userId != null) {
            println("ggg")
            val notifications = userNotificationRepository.markAllViewed(userId)
            println("pizdec")
            notifications.forEach { it.viewed = true }
            println("ipiipipi")
            userNotificationRepository.saveAll(notifications)
        }
    }

    fun deleteOldViewedNotifications() {
        val notifications = userNotificationRepository.findByViewedAndIsDeletedForDelete(true, false)
        notifications.forEach { it.isDeleted = true }
        userNotificationRepository.saveAll(notifications)
    }

    fun deleteAllViewedNotifications(){
        val notifications = userNotificationRepository.findByViewedAndIsDeleted(true, true)
        notifications.forEach{
            userNotificationRepository.delete(it)
        }

    }
    fun createAndSaveNotification(notificationRequest: NotificationRoot, notificationType:String): NotificationEntity {
        val notificationEntity = NotificationEntity(
            group = notificationRequest.notification.group,
            text = notificationRequest.notification.body.text,
            status = notificationRequest.notification.body.status.title,
            type = notificationRequest.notification.body.type.title,
            link = notificationRequest.notification.body.params.link,
            requestType = notificationRequest.notification.body.params.requestType,
            requestUrl = notificationRequest.notification.body.params.requestUrl,
            requestBody = notificationRequest.notification.body.params.requestBody,
            serviceName = notificationRequest.serviceName,
            notificationType = notificationType
        )
        return notificationRepository.save(notificationEntity)
    }

    fun linkNotificationToUser(userId: Long, notificationId: Long) {
        val userNotification = UserNotificationEntity(
            userId = userId,
            notificationId = notificationId,
            viewed = false,
            isDeleted = false
        )
        userNotificationRepository.save(userNotification)
    }
}

package com.example.Notifications.notification.controller

import com.example.Notifications.auth.service.AuthUserDefaultService
import com.example.Notifications.auth.service.AuthUserService
import com.example.Notifications.exception.AuthServiceInternalException
import com.example.Notifications.notification.data.NotificationRoot
import com.example.Notifications.notification.entity.NotificationEntity
import com.example.Notifications.notification.service.NotificationDataService
import com.example.Notifications.notification.service.NotificationService
import com.example.Notifications.updates.exception.ApiError
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class Controller(
    private val notificationService: NotificationService,
    private val notificationDataService: NotificationDataService,
    private val authUserService: AuthUserService,
    @Value("\${spring.app.password}")
    private val password:String,
    private val authUserDefaultService: AuthUserDefaultService
) {

    @PostMapping("/create")
    fun createNotification(
        @RequestHeader("Authorization") token: String,
        @RequestBody notificationRequest: NotificationRoot,
        @RequestParam userId: Long?,
        @RequestParam pass:String?
    ):ResponseEntity<List<NotificationEntity>>{
        if (pass != null) {
            if (pass != password) {
                throw ApiError.unauthorized("Неверный пароль доступа")
            }
        } else {
            val cleanToken = token.replace("Bearer ", "")
            authUserService.isTokenValid(cleanToken)
                ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyList())
            val admin = authUserDefaultService.getUserAdmin(cleanToken)
            if (admin != true) {
                throw AuthServiceInternalException("Отказано в доступе")
            }
        }
            val notification = notificationService.sendNotification(notificationRequest, userId)
            return ResponseEntity.ok(notification)
    }


    @GetMapping("/getAll")
    fun getAllNotification(): ResponseEntity<List<NotificationRoot>> {
        return ResponseEntity.ok(notificationDataService.getAllNotifications())
    }

    @GetMapping("/getAllNotDel")
    fun getAllNotDeleted(): ResponseEntity<List<NotificationRoot>> {
        return ResponseEntity.ok(notificationDataService.getAllNotDeleted())
    }

    @GetMapping("/markAllAsRead")
    fun checkSearchedAllNotifications(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        val cleanToken = token.replace("Bearer ", "")
        authUserService.getUserId(cleanToken)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Токен недействителен")

        return try {
            notificationDataService.markAllViewed(token)
            return ResponseEntity.ok("Уведомления успешно отмечены как прочитанные")
        }catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }
    @DeleteMapping("/delete")
    fun deleteNotification(
        @RequestHeader("Authorization") token: String,
        @RequestParam("id") id:Long
    ):ResponseEntity<String>{

        val cleanToken = token.replace("Bearer ", "")
        authUserService.isTokenValid(cleanToken)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Токен недействителен")
        return try {
            notificationDataService.deleteNotification(id)
            ResponseEntity.ok("Уведомление удалено")
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }
    @DeleteMapping("/deleteAllView")
    fun deleteAllViewedNotifications(
        @RequestHeader("Authorization") token: String,
    ):ResponseEntity<String>{
        val cleanToken = token.replace("Bearer ", "")
        authUserService.isTokenValid(cleanToken)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Токен недействителен")
        return try {
            notificationDataService.deleteAllViewedNotifications()
            ResponseEntity.ok("Уведомления удалены")
        }catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }
}
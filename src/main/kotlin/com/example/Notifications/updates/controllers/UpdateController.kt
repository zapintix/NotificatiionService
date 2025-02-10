package com.example.Notifications.updates.controllers

import com.example.Notifications.updates.data.UpdateRequest
import com.example.Notifications.updates.exception.ApiError
import com.example.Notifications.updates.service.UpdateService
import com.example.Notifications.updates.socket.UpdateSocket
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/updates")
class UpdateController(
    private val updateSocket: UpdateSocket,
    private val updateService: UpdateService,
    @Value("\${spring.app.password}")
    private val password: String
) {
    @PostMapping("/create")
    fun sendNewUpdate(
        @RequestParam pass: String?,
        @RequestBody updateRequest: UpdateRequest
    ): ResponseEntity<String> {
        if (pass != null) {
            if (pass != password) {
                throw ApiError.unauthorized("Неверный пароль доступа")
            }
        } else {
            throw ApiError.unauthorized("Пароль не передан")
        }

        if (updateRequest.updateTimeout == null) {
            throw ApiError.noContent("Отсутствует время до обновления")
        }

        val dbUpdate = updateService.createUpdate(updateRequest)
        updateSocket.sendUpdateSocket(dbUpdate)
        return ResponseEntity.ok("Обновление успешно отправлено")
    }
}
package com.example.Notifications.auth.service

import com.example.Notifications.auth.data.UserResponse
import com.example.Notifications.user.entity.UserEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class AuthUserDefaultRequester(
    private val restTemplate: RestTemplate
) {
    @Value("\${spring.app.auth.service.url}")
    private lateinit var authUrl: String


    fun getUserInfo(token: String): UserResponse {
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer $token")
        }
        val request = HttpEntity<Void>(headers)

        return try {
            val response: ResponseEntity<UserResponse> = restTemplate.exchange(
                authUrl,
                HttpMethod.GET,
                request,
                UserResponse::class.java
            )
            response.body ?: throw IllegalArgumentException("Ответ сервиса пустой")
        } catch (e: Exception) {
            throw IllegalArgumentException("Ошибка получения информации о пользователе: ${e.message}")
        }
    }


    fun validateToken(token: String): UserEntity? {
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer $token")
        }
        val request = HttpEntity<Void>(headers)

        return try {
            val response: ResponseEntity<Map<*, *>> = restTemplate.exchange(
                authUrl,
                HttpMethod.GET,
                request,
                Map::class.java
            )
            if (response.statusCode.is2xxSuccessful) {
                val userData = response.body
                println(userData)
                val userId = (userData?.get("id") as? Number)?.toLong()
                println(userId)
                val serviceName = userData?.get("serviceName") as? String ?: "rec"
                if (userId != null) {

                    return UserEntity(userId = userId, serviceName = serviceName)
                } else {
                    println("Не удалось получить userId из ответа.")
                }
            }
            null
        } catch (e: Exception) {
            println("Ошибка валидации токена: ${e.message}")
            null
        }
    }
}

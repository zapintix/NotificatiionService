package com.example.Notifications.auth

import com.example.Notifications.auth.service.AuthUserService
import com.example.Notifications.exception.AuthServiceInternalException
import com.example.Notifications.exception.jwt.InvalidTokenException
import com.example.Notifications.user.service.UserService
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component

@Component
class RmeSessionChannelInterceptor(
    private val authUserService: AuthUserService,
    private val userService: UserService,
) : ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val accessor = MessageHeaderAccessor.getAccessor(message,
            StompHeaderAccessor::class.java)

        if (StompCommand.CONNECT == accessor!!.command) {
            println(">>> WebSocket connection attempt")
            return processConnection(accessor, message, channel)
        }

        return message
    }

    private fun processConnection(
        stompHeaderAccessor: StompHeaderAccessor,
        message: Message<*>,
        channel: MessageChannel
    ): Message<*> {
        val token = stompHeaderAccessor.getNativeHeader("Authorization")?.firstOrNull()
            ?.replace("Bearer ", "")
            ?: throw InvalidTokenException("Authorization header is missing")

        println(">>> Token received: $token")

        // Проверка валидности токена
        val user = runCatching { authUserService.isTokenValid(token) }
            .getOrElse {
                sendError(channel, "500 Internal Server Error", "Auth service internal error.")
                throw AuthServiceInternalException("Error validating token: ${it.message}")
            }

        if (user == null) {
            sendError(channel, "401 Unauthorized", "Invalid token")
            throw InvalidTokenException("Token is invalid")
        }

        println(">>> Token validated successfully")

        // Получение ID пользователя
        val tokenUserId = runCatching { authUserService.getUserId(token) }
            .getOrElse {
                sendError(channel, "500 Internal Server Error", "Error retrieving user ID")
                throw AuthServiceInternalException("Error retrieving user ID: ${it.message}")
            }

        if (tokenUserId == null) {
            sendError(channel, "401 Unauthorized", "User not found")
            throw InvalidTokenException("User not found for the token")
        }

        println(">>> User ID: $tokenUserId")

        val dbUser = runCatching { userService.getUserByTokenId(tokenUserId) }
            .getOrNull()

        if (dbUser == null) {
            val newUser = runCatching { userService.createNewUser(user) }
                .getOrElse {
                    sendError(channel, "500 Internal Server Error", "Error creating user")
                    throw AuthServiceInternalException("Error creating user: ${it.message}")
                }
            println(">>> New user created: $newUser")
        }
        println(">>> User data: $dbUser")

        // Добавляю userId в сессию
        stompHeaderAccessor.sessionAttributes?.put("tokenUserId", tokenUserId)
        println(">>> User ID added to session: $tokenUserId")

        return message
    }

    private fun sendError(channel: MessageChannel, errorName: String, payload: String) {
        val response = MessageBuilder
            .withPayload(payload)
            .setHeader("error", errorName)
            .build()
        channel.send(response)
    }
}

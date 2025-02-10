package com.example.Notifications.updates.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ApiError(message: String) : RuntimeException(message) {
    companion object {
        fun unauthorized(message: String): UnauthorizedException {
            return UnauthorizedException(message)
        }

        fun noContent(message: String): NoContentException {
            return NoContentException(message)
        }
    }
}
package com.example.Notifications.auth.service

import com.example.Notifications.auth.data.UserResponse
import com.example.Notifications.user.entity.UserEntity
import org.springframework.stereotype.Service

@Service
interface AuthUserService {
    fun getUserId(token: String): Long?
    fun isTokenValid(token: String): UserEntity?
    fun getUser(token: String): UserResponse?
    fun getUserSub(token: String): String?
    fun getUserAdmin(token: String): Boolean?
}

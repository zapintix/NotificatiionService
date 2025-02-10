package com.example.Notifications.auth.service

import com.example.Notifications.auth.data.UserResponse
import com.example.Notifications.user.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class AuthUserDefaultService(
    val authUserRequester: AuthUserDefaultRequester,
): AuthUserService {

    override fun getUserId(token: String): Long? {
        return authUserRequester.getUserInfo(token).id
    }

    override fun getUser(token: String): UserResponse {
        return authUserRequester.getUserInfo(token)
    }
    override fun getUserSub(token: String): String? {
        return authUserRequester.getUserInfo(token).username
    }

    override fun isTokenValid(token: String): UserEntity? {
        return authUserRequester.validateToken(token)
    }

    override fun getUserAdmin(token: String): Boolean? {
        return authUserRequester.getUserInfo(token).superAdmin
    }

}

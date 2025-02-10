package com.example.Notifications.user.service

import com.example.Notifications.user.entity.UserEntity
import com.example.Notifications.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUserByTokenId(userId:Long): UserEntity?{
        return userRepository.getUserByUserId(userId)
    }
    fun getUserIdByTokenId(userId: Long): Long? {
        return userRepository.getUserByUserId(userId).id
    }
    fun findUserById(userId: Long):UserEntity{
        return userRepository.findUserById(userId)
    }
    fun createNewUser(user:UserEntity):UserEntity{
        return userRepository.save(user)
    }
    fun getAllUsersId():List<Long>{
        return userRepository.findAll().map { it.id!! }
    }
}
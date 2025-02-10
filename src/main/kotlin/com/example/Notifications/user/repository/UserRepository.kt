package com.example.Notifications.user.repository

import com.example.Notifications.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun getUserByUserId(userId:Long): UserEntity
    fun findUserById(userId: Long):UserEntity
}


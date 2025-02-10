package com.example.Notifications.updates.repository

import com.example.Notifications.updates.entity.UpdateEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UpdateRepository: JpaRepository<UpdateEntity, Long>
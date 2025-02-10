package com.example.Notifications.updates.service

import com.example.Notifications.updates.data.UpdateRequest
import com.example.Notifications.updates.entity.UpdateEntity
import com.example.Notifications.updates.repository.UpdateRepository
import org.springframework.stereotype.Service

@Service
class UpdateService(
    private val updateRepository: UpdateRepository,
) {

    fun createUpdate(updateRequest: UpdateRequest): UpdateRequest {
        val updateEntity = UpdateEntity(
            title = updateRequest.title,
            text = updateRequest.text,
            updateTimeout = updateRequest.updateTimeout!!,
            version = updateRequest.version
        )
        val savedUpdate = updateRepository.save(updateEntity)
        return savedUpdate.toUpdateRequest()
    }

    fun getAllUpdates():List<UpdateRequest>{
        return updateRepository.findAll()
            .map { update ->
                update.toUpdateRequest()
            }
    }
}
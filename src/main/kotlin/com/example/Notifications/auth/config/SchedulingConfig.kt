package com.example.Notifications.auth.config

import com.example.Notifications.notification.service.NotificationDataService
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Configuration
@EnableScheduling
@Component
class SchedulingConfig(
    private val notificationDataService: NotificationDataService
) {
    @Scheduled(fixedRate =  15 * 60 * 1000)
    fun deleteOldViewedNotifications() {
        notificationDataService.deleteOldViewedNotifications()

    }
}


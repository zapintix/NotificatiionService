package com.example.Notifications.notification.validator

import com.example.Notifications.notification.validator.ValuesValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FIELD,
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ValuesValidator::class])
annotation class Values(
    val acceptedValues: Array<String> = [],
    val message: String ="Invalid value.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
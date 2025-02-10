package com.example.Notifications.notification.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValuesValidator: ConstraintValidator<Values, CharSequence> {
    private val acceptedValues: MutableList<String> = mutableListOf()

    override fun initialize(constraintAnnotation: Values) {
        super.initialize(constraintAnnotation)

        acceptedValues.addAll(constraintAnnotation.acceptedValues)
    }

    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
        return if (value == null) {
            true
        } else acceptedValues.contains(value.toString())

    }
}
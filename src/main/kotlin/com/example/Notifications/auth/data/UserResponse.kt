package com.example.Notifications.auth.data

import com.example.Notifications.auth.data.UserAuthority
import com.example.Notifications.auth.data.UserOptions
import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    val id: Long? = null,

    @JsonProperty("username")
    val username: String? = null,

    @JsonProperty("firstname")
    val firstName: String? = null,

    @JsonProperty("lastname")
    val lastName: String? = null,

    @JsonProperty("secondname")
    val secondName: String? = null,

    val email: String? = null,
    val enabled: Boolean? = null,
    val referenceId: Int? = null,
    val orgOidRestriction: String? = null,
    val lastPasswordResetDate: String? = null,

    @JsonProperty("authorities")
    val authorities: Set<UserAuthority> = emptySet(),

    val organization: Int? = null,
    val organizationName: String? = null,
    val maxInactiveTime: Int? = null,
    val logEnabled: Boolean? = null,

    @JsonProperty("options")
    val options: UserOptions? = null,

    @JsonProperty("medStaff")
    val medStaff: Boolean? = null,

    @JsonProperty("superAdmin")
    val superAdmin: Boolean? = null
)

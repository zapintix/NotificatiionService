package com.example.Notifications.user.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false, unique = true)
    val userId: Long,

    @Column(name = "serviceName", nullable = false)
    val serviceName: String? = "rec",
)
 {

}
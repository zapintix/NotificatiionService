package com.example.Notifications.auth.controllers

import com.example.Notifications.auth.data.UserResponse
import com.example.Notifications.auth.service.AuthUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authUserService: AuthUserService
) {
    @GetMapping("/validate")
    fun validateToken(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        val cleanToken = token.replace("Bearer ", "")
        return if (authUserService.isTokenValid(cleanToken) !=null){
            ResponseEntity.ok("Token is valid")
        }else{
            ResponseEntity.status(401).body("Token is invalid")
        }
    }
    @GetMapping("/user")
    fun getUserInfo(@RequestHeader("Authorization") token: String): ResponseEntity<UserResponse> {
        val cleanToken = token.replace("Bearer ", "")
        if (authUserService.isTokenValid(cleanToken)==null) {
            return ResponseEntity.status(401).build()
        }

        val user = authUserService.getUser(cleanToken)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.status(401).build()
        }
    }

}
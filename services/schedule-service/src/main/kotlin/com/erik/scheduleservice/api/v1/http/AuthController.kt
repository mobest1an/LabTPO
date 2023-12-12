package com.erik.scheduleservice.api.v1.http

import com.erik.scheduleservice.api.v1.http.requests.AuthRequest
import com.erik.scheduleservice.api.v1.http.views.LoginView
import com.erik.scheduleservice.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): LoginView {
        return authService.login(request)
    }
}
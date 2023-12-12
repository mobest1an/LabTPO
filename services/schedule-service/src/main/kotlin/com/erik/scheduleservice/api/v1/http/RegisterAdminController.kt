package com.erik.scheduleservice.api.v1.http

import com.erik.scheduleservice.api.v1.http.requests.AuthRequest
import com.erik.scheduleservice.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/register")
class RegisterAdminController(
    private val authService: AuthService,
) {

    @PostMapping("/admin")
    fun registerAdmin(@RequestBody request: AuthRequest) {
        authService.registerAdmin(request)
    }

    @PostMapping("/superuser")
    fun registerSuperUser(@RequestBody request: AuthRequest) {
        authService.registerSuperUser(request)
    }
}

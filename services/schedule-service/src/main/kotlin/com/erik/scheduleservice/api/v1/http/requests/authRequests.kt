package com.erik.scheduleservice.api.v1.http.requests

data class AuthRequest(
    val username: String,
    val password: String,
)

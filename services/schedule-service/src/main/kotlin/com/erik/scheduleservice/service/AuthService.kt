package com.erik.scheduleservice.service

import com.erik.common.exception.AlreadyExistsException
import com.erik.common.exception.NotFoundException
import com.erik.common.user.Role
import com.erik.scheduleservice.api.v1.http.requests.AuthRequest
import com.erik.scheduleservice.api.v1.http.views.LoginView
import com.erik.scheduleservice.security.JwtUtils
import com.erik.scheduleservice.security.UserDetails
import com.erik.scheduleservice.security.UserDetailsServiceImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsServiceImpl,
    private val jwtUtils: JwtUtils,
    private val encoder: BCryptPasswordEncoder,
) {

    fun login(request: AuthRequest): LoginView {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.username,
                    request.password,
                )
            )
        } catch (e: Exception) {
            throw Exception("Bad Credentials")
        }

        val user: UserDetails = userDetailsService.loadUserByUsername(request.username)
        val token: String = jwtUtils.generateToken(user.username, user.roles)
        return LoginView(token)
    }

    fun registerSuperUser(request: AuthRequest) {
        try {
            userDetailsService.loadUserByUsername(request.username)
            throw AlreadyExistsException("User already exists")
        } catch (e: NotFoundException) {
            userDetailsService.save(UserDetails(request.username, encoder.encode(request.password), mutableSetOf(Role.SUPERUSER)), 0)
        }
    }

    fun registerAdmin(request: AuthRequest) {
        try {
            userDetailsService.loadUserByUsername(request.username)
            throw AlreadyExistsException("User already exists")
        } catch (e: NotFoundException) {
            userDetailsService.save(UserDetails(request.username, encoder.encode(request.password), mutableSetOf(Role.ADMIN)), 0)
        }
    }
}

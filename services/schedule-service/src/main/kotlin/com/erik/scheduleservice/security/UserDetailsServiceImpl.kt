package com.erik.scheduleservice.security

import com.erik.common.user.UserService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userService: UserService,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = getUserByUsername(username)
        return UserDetails(user.username, user.password, user.roles)
    }

    private fun getUserByUsername(username: String) =
        userService.findByUsername(username)

    fun save(userDetails: UserDetails, id: Long): UserDetails {
        val user = userDetails.toEntity(id)
        val entity = userService.save(user)
        return UserDetails(entity.username, entity.password, entity.roles)
    }
}

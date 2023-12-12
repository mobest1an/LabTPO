package com.erik.scheduleservice.config

import com.erik.common.user.Role
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class TokenBasedAuthentication(
    val user: AuthorizedUser,
    val token: String,
    val role: Role,
): AbstractAuthenticationToken(
    role.permissions.map {
        GrantedAuthority { it }
    }
) {
    override fun getCredentials(): Any {
        return ""
    }

    override fun getPrincipal(): Any {
        return "access-token"
    }
}

data class AuthorizedUser(
    val username: String,
    val roles: MutableSet<Role>,
)

fun unauthenticatedUser(): AuthorizedUser = AuthorizedUser(
    username = "",
    roles = mutableSetOf()
)

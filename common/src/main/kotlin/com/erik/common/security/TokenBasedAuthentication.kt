package com.erik.common.security

import com.erik.common.exception.InternalServerException
import com.erik.common.user.Role
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

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
    username = "default_user",
    roles = mutableSetOf(Role.USER)
)

fun getUserFromContext(): AuthorizedUser {
    val context = SecurityContextHolder.getContext()
        ?: throw InternalServerException("Can't retrieve user when security context is null")
    val authentication = context.authentication
        ?: throw InternalServerException("Can't retrieve user when authentication object in security context is null")
    if (!authentication.isAuthenticated) {
        throw InternalServerException("Can't retrieve user when authentication.IsAuthenticated is False")
    }
    if (authentication !is TokenBasedAuthentication) {
        throw InternalServerException("Can't retrieve user when authentication object in security context " +
                "is not an instance of ${TokenBasedAuthentication::class.java.name}")
    }
    return authentication.user
}

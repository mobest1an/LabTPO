package com.erik.scheduleservice.security

import com.erik.common.user.Role
import com.erik.common.user.User
import com.erik.common.utils.resolveRoles
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetails(
    private val username: String,
    private val password: String,
    val roles: MutableSet<Role>
) : UserDetails {

    override fun getAuthorities(): List<GrantedAuthority> {
        val role = resolveRoles(roles)
        return role.permissions.map {
            GrantedAuthority { it }
        }
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun toEntity(id: Long = 0L) = User(
        id,
        username,
        password,
        roles
    )
}

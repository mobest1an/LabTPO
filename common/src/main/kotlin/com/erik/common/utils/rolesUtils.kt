package com.erik.common.utils

import com.erik.common.user.Role

fun resolveRoles(roles: MutableSet<Role>): Role {
    roles.add(Role.USER)
    return if (roles.size > 1) {
        if (roles.contains(Role.SUPERUSER)) Role.SUPERUSER
        else if (roles.contains(Role.ADMIN)) Role.ADMIN
        else Role.USER
    } else Role.USER
}

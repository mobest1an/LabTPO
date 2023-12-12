package com.erik.common.user

import com.erik.common.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun save(userEntity: User): User = userRepository.save(userEntity)

    fun findByUsername(username: String): User = userRepository.findByUsername(username).orElseThrow { NotFoundException("User not found") }
}

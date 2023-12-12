package com.erik.scheduleservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.web.servlet.invoke

@Configuration
class SecurityConfig {

    @Bean
    fun web(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { disable() }
            csrf { disable() }
            authorizeHttpRequests {
//                authorize("")
            }
        }
        return http.build()
    }
}

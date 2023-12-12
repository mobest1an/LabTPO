package com.erik.scheduleservice.config

import com.erik.common.user.Role
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

@Configuration
class SecurityConfig(
    @Value("\${public.key.filename}")
    val publicKeyFileName: String,
) {

    @Bean
    fun web(http: HttpSecurity): SecurityFilterChain {
        val publicKey: PublicKey
        val key = Files.readAllBytes(Paths.get(publicKeyFileName))
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(key)
        publicKey = keyFactory.generatePublic(keySpec)

        val authTokenFilter = AuthTokenFilter(publicKey)
        http {
            addFilterBefore<UsernamePasswordAuthenticationFilter>(authTokenFilter)
            cors { disable() }
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/api/v1/market/admin/**", hasAuthority("MARKET_EDIT"))
                authorize("/api/v1/schedule/admin/**", hasAnyAuthority("SCHEDULE_EDIT"))
                authorize("/api/v1/**", permitAll)
                authorize("/error", permitAll)

                authorize(anyRequest, denyAll)
            }
        }

        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

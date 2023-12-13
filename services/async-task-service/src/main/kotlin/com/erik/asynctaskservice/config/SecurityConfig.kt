package com.erik.asynctaskservice.config

import com.erik.common.security.AuthTokenFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
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
                authorize("/api/v1/**", hasAuthority("MARKET_EDIT"))
                authorize("/error", permitAll)

                authorize(anyRequest, denyAll)
            }
        }

        return http.build()
    }
}

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }
}

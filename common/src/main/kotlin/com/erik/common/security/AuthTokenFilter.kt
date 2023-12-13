package com.erik.common.security

import com.erik.common.user.Role
import com.erik.common.utils.log
import com.erik.common.utils.resolveRoles
import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.core.context.SecurityContextHolder
import java.security.PublicKey
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthTokenFilter(
    private val publicKey: PublicKey
) : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request!! as HttpServletRequest
        val httpResponse = response!! as HttpServletResponse
        val filterChain = chain!!

        if (httpRequest.getHeader("Authorization") == null && httpRequest.getHeader("ServiceAuthorization") == null) {
            log.debug("Authorization header is null, therefore request is denied")
            denyRequest(httpRequest, httpResponse, filterChain)
            return
        }

        var token = if (httpRequest.getHeader("ServiceAuthorization") != null) {
            httpRequest.getHeader("ServiceAuthorization")
        } else {
            httpRequest.getHeader("Authorization")
        }
        val parts = token.split(" ")
        if (parts[0] != "Bearer") {
            log.debug("Bad JWT token $token was received, the user was rejected")
            denyRequest(httpRequest, httpResponse, filterChain)
            return
        }
        token = parts[1]

        val claims: Claims
        try {
            claims = validateToken(token)
        } catch (e: Exception) {
            log.debug("Bad JWT token $token was received, the user was rejected")
            denyRequest(httpRequest, httpResponse, filterChain)
            return
        }

        val username = claims["username"] as String
        val roles = claims["roles"] as ArrayList<String>

        val user = AuthorizedUser(username, roles.map { Role.valueOf(it) }.toMutableSet())
        authenticateRequest(
            httpRequest,
            httpResponse,
            chain,
            user,
            token,
        )
    }

    private fun validateToken(authToken: String): Claims {
        try {
            return Jwts.parserBuilder().setSigningKey(this.publicKey).build().parseClaimsJws(authToken).body
        } catch (ex: SignatureException) {
            throw java.lang.IllegalArgumentException("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            throw java.lang.IllegalArgumentException("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            throw java.lang.IllegalArgumentException("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            throw java.lang.IllegalArgumentException("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            throw java.lang.IllegalArgumentException("JWT claims string is empty.")
        }
    }

    private fun authenticateRequest(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        user: AuthorizedUser,
        token: String
    ) {
        val securityContext = SecurityContextHolder.getContext()
        val authentication = TokenBasedAuthentication(
            user = user,
            token = token,
            role = resolveRoles(user.roles)
        )

        authentication.isAuthenticated = true
        securityContext.authentication = authentication

        chain.doFilter(request, response)
    }

    private fun denyRequest(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val securityContext = SecurityContextHolder.getContext()
        val authentication = TokenBasedAuthentication(
            unauthenticatedUser(),
            token = "",
            role = Role.USER
        )
        authentication.isAuthenticated = true
        securityContext.authentication = authentication

        chain.doFilter(request, response)
    }
}

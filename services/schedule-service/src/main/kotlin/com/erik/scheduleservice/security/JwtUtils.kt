package com.erik.scheduleservice.security

import com.erik.common.exception.InternalServerException
import com.erik.common.user.Role
import com.erik.scheduleservice.config.AuthorizedUser
import com.erik.scheduleservice.config.TokenBasedAuthentication
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Service
class JwtUtils(
    @Value("\${private.key.filename}")
    privateKeyFileName: String,
    @Value("\${public.key.filename}")
    publicKeyFileName: String,

    @Value("\${jwt.expiration-time:1800000}") //по деволту полчаса
    private val expirationTime: Long,
) {
    private val privateKey: PrivateKey
    private val publicKey: PublicKey

    init {
        val key = Files.readAllBytes(Paths.get(privateKeyFileName))
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(key)
        privateKey = keyFactory.generatePrivate(keySpec)
    }

    init {
        val key = Files.readAllBytes(Paths.get(publicKeyFileName))
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(key)
        publicKey = keyFactory.generatePublic(keySpec)
    }

    fun generateToken(username: String, roles: MutableSet<Role>): String {
        return generateToken(username, roles, this.privateKey)
    }

    fun generateToken(username: String, roles: MutableSet<Role>, privateKey: PrivateKey): String {
        val claims: MutableMap<String, Any?> = HashMap()
        claims["username"] = username
        claims["roles"] = roles
        val expirationTime = Date(Date().time + this.expirationTime)
        return Jwts.builder().setClaims(claims).setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.RS512, privateKey).compact()
    }
}

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

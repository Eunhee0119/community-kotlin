package com.example.study.auth.token

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenProvider (
    private val userDetailsService : UserDetailsService
        ){


    @Value("\${security.jwt.secret-key}")
    private lateinit var secretKey: String

    @Value("\${security.jwt.token.expire-length}")
    private lateinit var accessExpireTime : Number

    @Value("\${security.jwt.token.refresh-expire-length}")
    private lateinit var refreshExpireTime: Number

    private val key by lazy{ Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))}

    fun generateToken(authentication: Authentication): String {
        return getToken(authentication,accessExpireTime.toLong())
    }

    fun generateRefreshToken(authentication: Authentication): String? {
        return getToken(authentication,refreshExpireTime.toLong())
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun getPrincipal(token: String?): String {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
    }

    fun getAuthentication(token: String?): Authentication {
        val username: String = getPrincipal(token)
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }


    fun validateToken(token: String?): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun getToken(authentication: Authentication, expireTime: Long): String {
        val claims = Jwts.claims().setSubject(authentication.name)
        val now = Date()
        val expiresIn = Date(now.time + expireTime)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiresIn)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }
}
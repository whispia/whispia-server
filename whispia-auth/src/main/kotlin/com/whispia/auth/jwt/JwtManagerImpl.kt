package com.whispia.auth.jwt

import com.whispia.auth.jwt.dto.TokenInfo
import com.whispia.auth.jwt.interfaces.JwtManager
import com.whispia.common.global.config.logger
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtManagerImpl(
    private val jwtProperties: JwtProperties
) : JwtManager {

    private val log = logger()
    private val key: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    override fun generateToken(email: String, authorities: Collection<String>): TokenInfo {
        val now = Date()
        val accessTokenExpiresIn = Date(now.time + jwtProperties.accessTokenValidityInSeconds * 1000)
        val refreshTokenExpiresIn = Date(now.time + jwtProperties.refreshTokenValidityInSeconds * 1000)

        val authoritiesString = authorities.joinToString(",")

        // Access Token 생성
        val accessToken = Jwts.builder()
            .subject(email)
            .claim("auth", authoritiesString)
            .issuedAt(now)
            .expiration(accessTokenExpiresIn)
            .signWith(key)
            .compact()

        // Refresh Token 생성
        val refreshToken = Jwts.builder()
            .expiration(refreshTokenExpiresIn)
            .signWith(key)
            .compact()

        return TokenInfo("Bearer", accessToken, refreshToken)
    }

    override fun validateToken(token: String): Boolean {
        try {
            parseClaims(token)
            return true
        } catch (e: SecurityException) {
            log.warn("Invalid JWT signature.", e)
        } catch (e: MalformedJwtException) {
            log.warn("Invalid JWT token.", e)
        } catch (e: ExpiredJwtException) {
            log.warn("Expired JWT token.", e)
        } catch (e: UnsupportedJwtException) {
            log.warn("Unsupported JWT token.", e)
        } catch (e: IllegalArgumentException) {
            log.warn("JWT claims string is empty.", e)
        }
        return false
    }

    override fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token)
        val authorities = claims["auth"]?.toString()?.split(",")
            ?.map { SimpleGrantedAuthority(it) }
            ?: emptyList()

        val principal = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    override fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    private fun parseClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}

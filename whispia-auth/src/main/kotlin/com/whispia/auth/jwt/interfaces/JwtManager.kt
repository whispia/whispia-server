package com.whispia.auth.jwt.interfaces

import com.whispia.auth.jwt.dto.TokenInfo
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication

interface JwtManager {

    fun generateToken(email: String, authorities: Collection<String>): TokenInfo
    fun validateToken(token: String): Boolean
    fun getAuthentication(token: String): Authentication
    fun resolveToken(request: HttpServletRequest): String?
}

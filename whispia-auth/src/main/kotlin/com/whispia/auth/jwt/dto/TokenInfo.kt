package com.whispia.auth.jwt.dto

data class TokenInfo(
    val grantType: String,      // 토큰 타입 (e.g., "Bearer")
    val accessToken: String,    // API 접근을 위한 Access Token
    val refreshToken: String,   // Access Token 재발급을 위한 Refresh Token
)

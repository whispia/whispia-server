package com.whispia.auth.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(

    val secret: String,
    val accessTokenValidityInSeconds: Long,
    val refreshTokenValidityInSeconds: Long
)

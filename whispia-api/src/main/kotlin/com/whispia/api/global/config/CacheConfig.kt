package com.whispia.api.global.config

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class CacheConfig {

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        // 각 캐시 타입별 설정 생성
        val cacheConfigurations = CacheType.entries.associate { cacheType ->
            cacheType.cacheName to createCacheConfiguration(cacheType.cacheName, cacheType.ttl, cacheType.typeReference)
        }

        return RedisCacheManager.builder(redisConnectionFactory)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build()
    }

    private fun createCacheConfiguration(
        cacheName: String,
        ttl: Duration,
        typeReference: TypeReference<*>
    ): RedisCacheConfiguration {
        val objectMapper = ObjectMapper()
            .registerModule(JavaTimeModule()) // LocalDateTime 등 Java 8 시간 타입 지원
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 날짜를 타임스탬프가 아닌 문자열로 직렬화

        val type = objectMapper.typeFactory.constructType(typeReference)
        val jsonSerializer = Jackson2JsonRedisSerializer<Any>(objectMapper, type)

        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(ttl) // TTL 설정
            .disableCachingNullValues() // null 값 캐싱 비활성화
            .computePrefixWith { "$cacheName::" } // 캐시 키 앞에 prefix 추가
            .serializeKeysWith( // 키 직렬화
                RedisSerializationContext.SerializationPair
                    .fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith( // 값 직렬화
                RedisSerializationContext.SerializationPair
                    .fromSerializer(jsonSerializer)
            )
    }
}

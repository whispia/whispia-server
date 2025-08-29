package com.whispia.api.global.config

import com.fasterxml.jackson.core.type.TypeReference
import com.whispia.api.worry.domain.Worry
import java.time.Duration

enum class CacheType(
    val cacheName: String,
    val ttl: Duration,
    val typeReference: TypeReference<*>
) {
    // 고민
    WORRY(CacheName.WORRY, Duration.ofHours(1), object : TypeReference<Worry>() {}),
    WORRIES(CacheName.WORRIES, Duration.ofHours(4), object : TypeReference<List<Worry>>() {}),
    WORRIES_TOTAL_COUNT(CacheName.WORRIES_TOTAL_COUNT, Duration.ofHours(4), object : TypeReference<Long>() {})

}

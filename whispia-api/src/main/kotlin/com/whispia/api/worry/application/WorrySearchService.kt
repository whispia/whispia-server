package com.whispia.api.worry.application

import com.whispia.api.global.config.CacheName
import com.whispia.api.worry.application.dto.WorrySearchInput
import com.whispia.api.worry.domain.Worry
import com.whispia.api.worry.domain.repository.WorryRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class WorrySearchService(
    private val worryRepository: WorryRepository
) {
    @Cacheable(value = [CacheName.WORRIES], key = "#input.toString()")
    fun search(input: WorrySearchInput): List<Worry> {
        return worryRepository.search(input)
    }

    @Cacheable(value = [CacheName.WORRIES_TOTAL_COUNT], key = "#input.toString()")
    fun getTotalCount(input: WorrySearchInput) : Long {
        return worryRepository.getTotalCount(input)
    }

    @Cacheable(value = [CacheName.WORRY], key = "#worryId")
    fun searchDetail(worryId: Long): Worry {
        return worryRepository.findById(worryId)
    }
}

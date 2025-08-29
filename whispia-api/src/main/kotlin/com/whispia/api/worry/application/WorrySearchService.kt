package com.whispia.api.worry.application

import com.whispia.api.worry.application.dto.WorrySearchInput
import com.whispia.api.worry.domain.Worry
import com.whispia.api.worry.domain.repository.WorryRepository
import org.springframework.stereotype.Service

@Service
class WorrySearchService(
    private val worryRepository: WorryRepository
) {
    fun search(input: WorrySearchInput): List<Worry> {
        return worryRepository.search(input)
    }

    fun getTotalCount(input: WorrySearchInput) : Long {
        return worryRepository.getTotalCount(input)
    }

    fun searchDetail(worryId: Long): Worry {
        return worryRepository.findById(worryId)
    }
}

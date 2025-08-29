package com.whispia.api.worry.domain.repository

import com.whispia.api.worry.application.dto.WorrySearchInput
import com.whispia.api.worry.domain.Worry

interface WorryRepository {

    fun save(worry: Worry): Boolean

    fun search(input: WorrySearchInput): List<Worry>

    fun getTotalCount(input: WorrySearchInput): Long

    fun findById(worryId: Long): Worry
}

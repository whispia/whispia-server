package com.whispia.api.worry.application.dto

import com.whispia.api.worry.domain.WorryCategory
import com.whispia.api.worry.domain.WorryStatus
import com.whispia.common.global.model.dto.PaginationInput

data class WorrySearchInput(
    val userId: Long?,
    val category: WorryCategory?,
    val status: WorryStatus?,
    val pagination: PaginationInput
)

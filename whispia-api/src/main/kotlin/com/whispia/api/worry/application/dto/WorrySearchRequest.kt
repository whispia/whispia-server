package com.whispia.api.worry.application.dto

import com.whispia.api.worry.domain.WorryCategory
import com.whispia.api.worry.domain.WorryStatus
import com.whispia.common.global.model.dto.PaginationRequest

data class WorrySearchRequest (
    val userId: Long?,
    val category: String?,
    val status: String?
) {
    fun toInput(paginationRequest: PaginationRequest): WorrySearchInput {
        return WorrySearchInput(
            userId = userId,
            category = category?.let { WorryCategory.valueOf(it) },
            status = status?.let { WorryStatus.valueOf(it) },
            pagination = paginationRequest.toInput()
        )
    }
}

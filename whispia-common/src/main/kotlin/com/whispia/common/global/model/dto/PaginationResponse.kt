package com.whispia.common.global.model.dto

import com.whispia.common.global.model.Pagination

data class PaginationResponse<T>(
    val data: List<T>,
    val pagination: Pagination
) {
    companion object {
        fun <T> of(
            data: List<T>,
            paginationInput: PaginationInput,
            totalCount: Long
        ): PaginationResponse<T> {

            return PaginationResponse(
                data = data,
                pagination = Pagination.of(paginationInput, totalCount)
            )
        }
    }
}

package com.whispia.common.global.model

import com.whispia.common.global.model.dto.PaginationInput

data class Pagination(
    val page: Long,
    val pageSize: Long,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
    val totalPages: Long,
    val totalCount: Long
) {
    companion object {
        fun of(
            paginationInput: PaginationInput,
            totalCount: Long
        ): Pagination {

            val page = paginationInput.page
            val pageSize = paginationInput.pageSize

            val totalPages = when {
                pageSize == 0L -> 0L
                totalCount == 0L -> 0L
                else -> (totalCount + pageSize - 1L) / pageSize
            }

            return Pagination(
                page = paginationInput.page,
                pageSize = paginationInput.pageSize,
                hasPrevious = paginationInput.page > 0L,
                hasNext = page < totalPages - 1L,
                totalPages = totalPages,
                totalCount = totalCount
            )
        }
    }
}

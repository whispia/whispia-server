package com.whispia.common.global.model.dto

data class PaginationInput(
    val page: Long,
    val pageSize: Long
) {
    companion object {
        fun of(page: Long = 0, pageSize: Long = 10L): PaginationInput {
            return PaginationInput(page * pageSize, pageSize)
        }
    }
}

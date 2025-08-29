package com.whispia.api.worry.application.dto

import com.whispia.api.worry.domain.Worry
import com.whispia.api.worry.domain.WorryCategory
import com.whispia.api.worry.domain.WorryStatus

data class WorrySearchResponse (
    val id: Long,
    val title: String,
    val content: String,
    val category: WorryCategory,
    val status: WorryStatus,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        fun listFrom(worries: List<Worry>): List<WorrySearchResponse> {
            return worries.map { worry ->
                from(worry)
            }
        }

        fun from(worry: Worry): WorrySearchResponse {
            return WorrySearchResponse(
                id = worry.id!!,
                title = worry.title,
                content = worry.content,
                category = worry.category,
                status = worry.status,
                createdAt = worry.createdAt.toString(),
                updatedAt = worry.updatedAt.toString()
            )
        }
    }
}

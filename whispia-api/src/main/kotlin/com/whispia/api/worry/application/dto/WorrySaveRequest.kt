package com.whispia.api.worry.application.dto

import com.whispia.api.worry.domain.WorryCategory
import com.whispia.api.worry.domain.WorryStatus

data class WorrySaveRequest(
    val userId: Long,
    val title: String,
    val content: String,
    val category: String,
    val status: String
) {
    fun toInput(): WorrySaveInput {
        return WorrySaveInput(
            userId = userId,
            title = title,
            content = content,
            category = WorryCategory.valueOf(category),
            status = WorryStatus.valueOf(status)
        )
    }
}

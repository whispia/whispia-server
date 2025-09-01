package com.whispia.api.worry.application.dto

import com.whispia.api.user.domain.User
import com.whispia.api.worry.domain.Worry
import com.whispia.api.worry.domain.WorryCategory
import com.whispia.api.worry.domain.WorryStatus

data class WorrySaveInput(
    val userId: Long,
    val title: String,
    val content: String,
    val category: WorryCategory,
    val status: WorryStatus,
    val hashtags: List<String>
) {
    fun toEntity(user: User) = Worry(
        title = title,
        content = content,
        category = category,
        status = status,
        user = user,
        hashtags = hashtags
    )
}

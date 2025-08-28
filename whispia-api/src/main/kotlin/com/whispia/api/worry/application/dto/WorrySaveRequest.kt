package com.whispia.api.worry.application.dto

import com.whispia.api.worry.domain.WorryCategory
import com.whispia.api.worry.domain.WorryStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class WorrySaveRequest(
    @field:NotBlank(message = "사용자 ID는 필수입니다")
    val userId: Long,

    @field:NotBlank(message = "제목은 필수입니다")
    @field:Size(max = 30, message = "제목은 30자 이내로 입력해주세요")
    val title: String,

    @field:NotBlank(message = "내용은 필수입니다")
    @field:Size(max = 500, message = "내용은 500자 이내로 입력해주세요")
    val content: String,

    @field:NotBlank(message = "카테고리는 필수입니다")
    val category: String,

    @field:NotBlank(message = "상태는 필수입니다")
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

package com.whispia.common.global.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.PositiveOrZero

data class PaginationRequest(
    @field:PositiveOrZero(message = "페이지는 음수일 수 없습니다.")
    val page: Int = 0,

    @field:Min(value = 1, message = "페이지 사이즈는 0보다 커야합니다.")
    val size: Int = 10,
)

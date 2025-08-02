package com.whispia.common.global.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseData<T>(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val httpStatus: Int,
    val errorCode: ErrorCode? = null,
    val data: T? = null
)

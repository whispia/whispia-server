package com.whispia.common.global.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseData<T>(
    val timestamp: LocalDateTime,
    val httpStatus: Int,
    val data: T? = null,
    val errorCode: ErrorCode? = null
) {
    private constructor(
        httpStatus: Int,
        data: T? = null,
        errorCode: ErrorCode? = null
    ) : this(
        timestamp = LocalDateTime.now(),
        httpStatus = httpStatus,
        data = data,
        errorCode = errorCode
    )

    companion object {
        fun <T> of(
            httpStatus: Int,
            data: T? = null,
            errorCode: ErrorCode? = null
        ): ResponseData<T> {
            return ResponseData(
                httpStatus = httpStatus,
                data = data,
                errorCode = errorCode
            )
        }
    }
}

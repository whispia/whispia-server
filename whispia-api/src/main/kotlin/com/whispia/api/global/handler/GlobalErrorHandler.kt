package com.whispia.api.global.handler

import com.whispia.common.global.config.logger
import com.whispia.common.global.dto.ErrorCode
import com.whispia.common.global.dto.ResponseData
import com.whispia.common.global.exception.BusinessException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private const val COMMA = ", "

@RestControllerAdvice
class GlobalErrorHandler {

    private val log = logger()

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ResponseData<Nothing>> {
        log.error("예상하지 못한 오류가 발생했습니다.", e)

        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        val responseData = ResponseData.of(
            httpStatus = errorCode.httpStatus.value(),
            errorCode = errorCode,
            data = null
        )

        return ResponseEntity(responseData, errorCode.httpStatus)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ResponseData<Nothing>> {
        log.error("파라미터 오류 - {}", e.message)

        val errorCode = ErrorCode.INVALID_PARAMETER
        val responseData = ResponseData.of(
            httpStatus = errorCode.httpStatus.value(),
            errorCode = errorCode,
            data = null
        )

        return ResponseEntity(responseData, errorCode.httpStatus)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ResponseData<Nothing>> {
        log.error("비즈니스 로직 중 에러 발생 - {}", e.message)

        val errorCode = ErrorCode.INVALID_PARAMETER
        val responseData = ResponseData.of(
            httpStatus = errorCode.httpStatus.value(),
            errorCode = errorCode,
            data = null
        )

        return ResponseEntity(responseData, errorCode.httpStatus)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ResponseData<Nothing>> {
        val fieldErrors = e.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
        log.error("요청 데이터 검증 실패 - {}", fieldErrors.joinToString(COMMA))

        val errorCode = ErrorCode.INVALID_PARAMETER
        val responseData = ResponseData.of(
            httpStatus = errorCode.httpStatus.value(),
            errorCode = errorCode,
            data = null
        )

        return ResponseEntity(responseData, errorCode.httpStatus)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<ResponseData<Nothing>> {
        val violations = e.constraintViolations.map { "${it.propertyPath}: ${it.message}" }
        log.error("제약 조건 위반 - {}", violations.joinToString(COMMA))

        val errorCode = ErrorCode.INVALID_PARAMETER
        val responseData = ResponseData.of(
            httpStatus = errorCode.httpStatus.value(),
            errorCode = errorCode,
            data = null
        )

        return ResponseEntity(responseData, errorCode.httpStatus)
    }
}

package com.whispia.common.global.exception

import com.whispia.common.global.dto.ErrorCode
import io.mockk.every
import io.mockk.mockk
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import kotlin.test.assertEquals

class GlobalErrorHandlerTest {

    private val globalErrorHandler = GlobalErrorHandler()

    @Test
    fun `일반 예외 처리 테스트`() {
        // given
        val exception = RuntimeException("테스트 예외")

        // when
        val response = globalErrorHandler.handleGenericException(exception)

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR, response.body?.errorCode)
        assertEquals(500, response.body?.httpStatus)
        assertNull(response.body?.data)
    }

    @Test
    fun `IllegalArgumentException 예외 처리 테스트`() {
        // given
        val exception = IllegalArgumentException("잘못된 파라미터")

        // when
        val response = globalErrorHandler.handleIllegalArgumentException(exception)

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorCode.INVALID_PARAMETER, response.body?.errorCode)
        assertEquals(400, response.body?.httpStatus)
        assertNull(response.body?.data)
    }

    @Test
    fun `BusinessException 처리 테스트`() {
        // given
        val exception = BusinessException("비즈니스 로직 에러")

        // when
        val response = globalErrorHandler.handleBusinessException(exception)

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorCode.INVALID_PARAMETER, response.body?.errorCode)
        assertEquals(400, response.body?.httpStatus)
        assertNull(response.body?.data)
    }

    @Test
    fun `MethodArgumentNotValidException 예외 처리 테스트`() {
        // given
        val bindingResult = mockk<BindingResult>()
        val fieldError = FieldError("objectName", "fieldName", "validation error message")
        every { bindingResult.fieldErrors } returns listOf(fieldError)

        val exception = MethodArgumentNotValidException(mockk(), bindingResult)

        // when
        val response = globalErrorHandler.handleValidationException(exception)

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorCode.INVALID_PARAMETER, response.body?.errorCode)
        assertEquals(400, response.body?.httpStatus)
        assertNull(response.body?.data)
    }

    @Test
    fun `ConstraintViolationException 예외 처리 테스트`() {
        // given
        val constraintViolation = mockk<ConstraintViolation<*>>()
        every { constraintViolation.propertyPath.toString() } returns "fieldName"
        every { constraintViolation.message } returns "constraint violation message"

        val violations = setOf(constraintViolation)
        val exception = ConstraintViolationException("제약 조건 위반", violations)

        // when
        val response = globalErrorHandler.handleConstraintViolationException(exception)

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorCode.INVALID_PARAMETER, response.body?.errorCode)
        assertEquals(400, response.body?.httpStatus)
        assertNull(response.body?.data)
    }
}

package com.whispia.api.worry.presentation

import com.whispia.api.worry.application.WorrySaveService
import com.whispia.api.worry.application.dto.WorrySaveRequest
import com.whispia.common.global.config.logger
import com.whispia.common.global.dto.ResponseData
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/worries")
class WorryApiController(
    private val worrySaveService: WorrySaveService
) {

    private val log = logger()

    @PostMapping
    fun create(@Valid @RequestBody request: WorrySaveRequest): ResponseData<Boolean> {
        log.info("WorryApiController.create - 고민 등록 요청: $request")
        return ResponseData.of(
            httpStatus = 201,
            data = worrySaveService.save(request.toInput())
        )
    }
}

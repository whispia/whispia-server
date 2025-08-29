package com.whispia.api.worry.presentation

import com.whispia.api.worry.application.WorrySaveService
import com.whispia.api.worry.application.WorrySearchService
import com.whispia.api.worry.application.dto.WorrySaveRequest
import com.whispia.api.worry.application.dto.WorrySearchRequest
import com.whispia.api.worry.application.dto.WorrySearchResponse
import com.whispia.common.global.config.logger
import com.whispia.common.global.model.dto.PaginationRequest
import com.whispia.common.global.model.dto.PaginationResponse
import com.whispia.common.global.model.ResponseData
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/worries")
class WorryApiController(
    private val worrySaveService: WorrySaveService,
    private val worrySearchService: WorrySearchService
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

    @GetMapping
    fun search(
        @Valid request: WorrySearchRequest,
        @Valid paginationRequest: PaginationRequest
    ): ResponseData<PaginationResponse<WorrySearchResponse>> {
        log.info("WorryApiController.search - 고민 조회 요청 : $request, $paginationRequest")
        val searchInput = request.toInput(paginationRequest)

        val worries = worrySearchService.search(searchInput)
        val response = WorrySearchResponse.listFrom(worries)

        val totalCount = worrySearchService.getTotalCount(searchInput)
        val paginationResponse = PaginationResponse.of(
            data = response,
            paginationInput = searchInput.pagination,
            totalCount = totalCount
        )

        return ResponseData.of(
            httpStatus = 200,
            data = paginationResponse
        )
    }

    @GetMapping("/{id}")
    fun searchDetail(@PathVariable id: Long): ResponseData<WorrySearchResponse> {
        log.info("WorryApiController.searchDetail - 고민 상세 조회 요청 : $id")
        val worry = worrySearchService.searchDetail(id)

        return ResponseData.of(
            httpStatus = 200,
            data = WorrySearchResponse.from(worry)
        )
    }
}

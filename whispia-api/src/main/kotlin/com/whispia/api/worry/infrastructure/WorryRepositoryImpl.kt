package com.whispia.api.worry.infrastructure

import com.whispia.api.worry.domain.Worry
import com.whispia.api.worry.domain.repository.WorryRepository
import com.whispia.common.global.config.logger
import org.springframework.stereotype.Repository

@Repository
class WorryRepositoryImpl(
    private val worryJpaRepository: WorryJpaRepository
) : WorryRepository {

    private val log = logger()

    override fun save(worry: Worry): Boolean {
        worryJpaRepository.save(worry)
        log.info("WorryRepository.save - 고민 저장 성공(idx: ${worry.id})")
        return true
    }
}

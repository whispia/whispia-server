package com.whispia.api.worry.infrastructure

import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import com.whispia.api.worry.application.dto.WorrySearchInput
import com.whispia.api.worry.domain.QWorry
import com.whispia.api.worry.domain.Worry
import com.whispia.api.worry.domain.repository.WorryRepository
import com.whispia.common.global.config.logger
import org.springframework.stereotype.Repository

@Repository
class WorryRepositoryImpl(
    private val worryJpaRepository: WorryJpaRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : WorryRepository {

    private val log = logger()

    override fun save(worry: Worry): Boolean {
        worryJpaRepository.save(worry)
        log.info("WorryRepository.save - 고민 저장 성공(idx: ${worry.id})")
        return true
    }

    override fun search(input: WorrySearchInput): List<Worry> {
        return searchQuery(input)
            .offset((input.pagination.page))
            .limit(input.pagination.pageSize)
            .fetch()
    }

    override fun getTotalCount(input: WorrySearchInput): Long {
        return searchQuery(input).fetch()
            .size
            .toLong()
    }

    private fun searchQuery(input: WorrySearchInput): JPAQuery<Worry> {
        val qWorry = QWorry.worry

        return jpaQueryFactory
            .selectFrom(qWorry)
            .leftJoin(qWorry.user).fetchJoin()
            .where(
                input.userId?.let { qWorry.user.id.eq(it) },
                input.category?.let { qWorry.category.eq(it) },
                input.status?.let { qWorry.status.eq(it) }
            )
    }

    override fun findById(worryId: Long): Worry {
        return worryJpaRepository.findById(worryId).orElseThrow { NoSuchElementException("Worry not found with id: $worryId") }
    }
}

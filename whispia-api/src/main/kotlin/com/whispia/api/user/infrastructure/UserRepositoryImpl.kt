package com.whispia.api.user.infrastructure

import com.whispia.api.user.domain.User
import com.whispia.api.user.domain.repository.UserRepository
import com.whispia.common.global.exception.BusinessException
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun findById(id: Long): User =
        userJpaRepository.findById(id)
            .orElseThrow { BusinessException("User not found: $id") }
}

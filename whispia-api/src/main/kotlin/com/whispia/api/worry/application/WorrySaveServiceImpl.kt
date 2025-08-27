package com.whispia.api.worry.application

import com.whispia.api.user.domain.repository.UserRepository
import com.whispia.api.worry.application.dto.WorrySaveInput
import com.whispia.api.worry.domain.repository.WorryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WorrySaveServiceImpl(
    private val userRepository: UserRepository,
    private val worryRepository: WorryRepository
) : WorrySaveService {

    @Transactional
    override fun save(input: WorrySaveInput): Boolean {
        val user = userRepository.findById(input.userId)

        val worry = input.toEntity(user)
        return worryRepository.save(worry)
    }
}

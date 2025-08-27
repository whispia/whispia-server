package com.whispia.api.user.domain.repository

import com.whispia.api.user.domain.User

interface UserRepository {
    fun findById(id: Long): User
}

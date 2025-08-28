package com.whispia.api.worry.domain.repository

import com.whispia.api.worry.domain.Worry

interface WorryRepository {

    fun save(worry: Worry): Boolean
}

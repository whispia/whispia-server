package com.whispia.api.worry.application

import com.whispia.api.worry.application.dto.WorrySaveInput

interface WorrySaveService {

    fun save(input: WorrySaveInput): Boolean
}

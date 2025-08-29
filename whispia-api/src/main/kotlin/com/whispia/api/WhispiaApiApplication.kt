package com.whispia.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
class WhispiaApiApplication

fun main(args: Array<String>) {
    runApplication<WhispiaApiApplication>(*args)
}

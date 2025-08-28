package com.whispia.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class WhispiaAuthApplication

fun main(args: Array<String>) {
    runApplication<WhispiaAuthApplication>(*args)
}

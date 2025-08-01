package com.whispia.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhispiaApiApplication

fun main(args: Array<String>) {
    runApplication<WhispiaApiApplication>(*args)
}

package com.whispia.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhispiaChatApplication

fun main(args: Array<String>) {
    runApplication<WhispiaChatApplication>(*args)
}

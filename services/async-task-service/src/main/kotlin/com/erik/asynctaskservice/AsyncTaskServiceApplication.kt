package com.erik.asynctaskservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AsyncTaskServiceApplication

fun main(args: Array<String>) {
    runApplication<AsyncTaskServiceApplication>(*args)
}

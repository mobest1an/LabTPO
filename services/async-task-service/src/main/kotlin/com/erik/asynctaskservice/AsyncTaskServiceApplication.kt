package com.erik.asynctaskservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EntityScan(basePackages = ["com.erik.common", "com.erik.asynctaskservice"])
@EnableJpaRepositories(basePackages = ["com.erik.common", "com.erik.asynctaskservice"])
@ComponentScan(basePackages = ["com.erik.common", "com.erik.asynctaskservice"])
@EnableAsync
class AsyncTaskServiceApplication

fun main(args: Array<String>) {
    runApplication<AsyncTaskServiceApplication>(*args)
}

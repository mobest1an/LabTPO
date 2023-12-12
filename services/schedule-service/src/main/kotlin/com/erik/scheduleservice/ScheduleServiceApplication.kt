package com.erik.scheduleservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan(basePackages = ["com.erik.common", "com.erik.scheduleservice"])
@EnableJpaRepositories(basePackages = ["com.erik.common", "com.erik.scheduleservice"])
@ComponentScan(basePackages = ["com.erik.common", "com.erik.scheduleservice"])
class ScheduleServiceApplication

fun main(args: Array<String>) {
    runApplication<ScheduleServiceApplication>(*args)
}

package com.erik.scheduleservice.api.v1.http

import com.erik.scheduleservice.api.v1.http.views.ScheduleView
import com.erik.scheduleservice.api.v1.http.views.toView
import com.erik.scheduleservice.service.ScheduleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/schedule")
class ScheduleController(
    private val scheduleService: ScheduleService,
) {

    @GetMapping("/{id}")
    fun getScheduleById(@PathVariable id: Long): ScheduleView {
        return scheduleService.getScheduleById(id).toView()!!
    }
}

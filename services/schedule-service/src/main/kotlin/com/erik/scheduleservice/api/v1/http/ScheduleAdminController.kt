package com.erik.scheduleservice.api.v1.http

import com.erik.scheduleservice.api.v1.http.requests.ScheduleUploadRequest
import com.erik.scheduleservice.api.v1.http.views.ScheduleView
import com.erik.scheduleservice.api.v1.http.views.toView
import com.erik.scheduleservice.service.ScheduleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/schedule/admin")
class ScheduleAdminController(
    private val scheduleService: ScheduleService,
) {

    @PostMapping
    fun createSchedule(@RequestBody request: ScheduleUploadRequest): ScheduleView {
        return scheduleService.createSchedule(request).toView()!!
    }

    @PutMapping("/{id}")
    fun updateSchedule(@PathVariable id: Long, @RequestBody request: ScheduleUploadRequest): ScheduleView {
        return scheduleService.updateSchedule(id, request).toView()!!
    }
}

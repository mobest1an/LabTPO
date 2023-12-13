package com.erik.asynctaskservice.api.v1.http

import com.erik.asynctaskservice.api.v1.http.requests.ScheduleBatchUploadRequest
import com.erik.asynctaskservice.api.v1.http.views.ScheduleUploadTaskView
import com.erik.asynctaskservice.api.v1.http.views.ScheduleView
import com.erik.asynctaskservice.api.v1.http.views.toView
import com.erik.asynctaskservice.service.ScheduleBatchUpdateService
import com.erik.asynctaskservice.service.ScheduleUploadTaskService
import com.erik.common.scheduleuploadtask.ScheduleUploadTask
import com.erik.common.security.getUserFromContext
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/schedule/")
class ScheduleBatchUpdateController(
    private val scheduleBatchUpdateService: ScheduleBatchUpdateService,
    private val scheduleUploadTaskService: ScheduleUploadTaskService,
) {

    @PostMapping
    fun updateBatchSchedule(@RequestBody request: ScheduleBatchUploadRequest) {
        val contextUser = getUserFromContext()
        scheduleBatchUpdateService.updateBatchSchedule(request, contextUser)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): ScheduleUploadTaskView {
        return scheduleUploadTaskService.getTaskById(id).toView()
    }

    @GetMapping
    fun getAll(): List<ScheduleUploadTaskView> {
        return scheduleUploadTaskService.getAll().map {
            it.toView()
        }
    }
}

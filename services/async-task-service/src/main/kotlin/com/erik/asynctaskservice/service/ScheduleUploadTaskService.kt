package com.erik.asynctaskservice.service

import com.erik.asynctaskservice.api.v1.http.requests.ScheduleTaskStatusUpdateRequest
import com.erik.asynctaskservice.dao.ScheduleUploadTaskRepository
import com.erik.common.exception.NotFoundException
import com.erik.common.market.Schedule
import com.erik.common.scheduleuploadtask.ScheduleUploadTask
import com.erik.common.scheduleuploadtask.TaskStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class ScheduleUploadTaskService(
    private val scheduleUploadTaskRepository: ScheduleUploadTaskRepository,
) {

    fun getTaskById(id: Long): ScheduleUploadTask {
        return scheduleUploadTaskRepository.findById(id).orElseThrow { NotFoundException("Task not found") }
    }

    fun getAll(): List<ScheduleUploadTask> {
        return scheduleUploadTaskRepository.findAll().toList()
    }

    fun createTask(schedules: MutableSet<Schedule>? = null): ScheduleUploadTask {
        val task = ScheduleUploadTask(
            id = 0L,
            status = TaskStatus.IN_PROGRESS,
            startedAt = Date(),
            schedules = schedules,
        )

        return save(task)
    }

    fun updateTask(id: Long, request: ScheduleTaskStatusUpdateRequest): ScheduleUploadTask {
        val task = getTaskById(id)
        task.status = request.status
        task.schedules = request.schedules

        return save(task)
    }

    private fun save(scheduleUploadTask: ScheduleUploadTask): ScheduleUploadTask {
        return scheduleUploadTaskRepository.save(scheduleUploadTask)
    }
}

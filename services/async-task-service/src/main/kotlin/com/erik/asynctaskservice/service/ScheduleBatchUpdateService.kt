package com.erik.asynctaskservice.service

import com.erik.asynctaskservice.api.v1.http.requests.ScheduleBatchUploadRequest
import com.erik.asynctaskservice.api.v1.http.requests.ScheduleTaskStatusUpdateRequest
import com.erik.asynctaskservice.api.v1.http.requests.ScheduleUploadRequest
import com.erik.asynctaskservice.dao.ScheduleUploadTaskRepository
import com.erik.common.exception.NotFoundException
import com.erik.common.market.MarketRepository
import com.erik.common.market.Schedule
import com.erik.common.market.ScheduleRepository
import com.erik.common.market.WeekDay
import com.erik.common.scheduleuploadtask.TaskStatus
import com.erik.common.security.AuthorizedUser
import com.erik.common.user.UserService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ScheduleBatchUpdateService(
    private val marketRepository: MarketRepository,
    private val scheduleRepository: ScheduleRepository,
    private val scheduleUploadTaskService: ScheduleUploadTaskService,
    private val userService: UserService,
) {

    @Async
    @Transactional
    fun updateBatchSchedule(request: ScheduleBatchUploadRequest, contextUser: AuthorizedUser) {
        val task = scheduleUploadTaskService.createTask()
        val schedules = mutableSetOf<Schedule>()
        try {
            for (upload in request.batch) {
                schedules.add(updateSchedule(upload.id, upload, contextUser))
            }
            scheduleUploadTaskService.updateTask(task.id, ScheduleTaskStatusUpdateRequest(TaskStatus.SUCCESS, schedules))
        } catch (e: Exception) {
            scheduleUploadTaskService.updateTask(task.id, ScheduleTaskStatusUpdateRequest(TaskStatus.ERROR, schedules))
        }
    }

    fun updateSchedule(id: Long = 0L, request: ScheduleUploadRequest, contextUser: AuthorizedUser): Schedule {
        val market = marketRepository.findById(request.marketId).orElseThrow { NotFoundException() }
        val schedule = Schedule(
            id = id,
            market = market
        )
        schedule.lastChange = Date()

        val user = userService.findByUsername(contextUser.username)
        schedule.lastChangeAuthor = user

        if (request.weekDayUploadRequests != null) {
            val weekDays = request.weekDayUploadRequests.map {
                WeekDay(
                    id = 0L,
                    dayNumber = it.dayNumber,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    schedule = schedule
                )
            }

            schedule.weekDays = mutableSetOf()
            schedule.weekDays.addAll(weekDays)
        }

        return save(schedule)
    }

    private fun save(schedule: Schedule): Schedule {
        return scheduleRepository.save(schedule)
    }
}

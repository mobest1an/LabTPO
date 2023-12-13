package com.erik.asynctaskservice.api.v1.http.requests

import com.erik.common.market.Schedule
import com.erik.common.scheduleuploadtask.TaskStatus
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class ScheduleBatchUploadRequest(
    val batch: MutableSet<ScheduleUploadRequest>
)

data class ScheduleUploadRequest(
    val id: Long = 0L,
    val marketId: Long,
    val weekDayUploadRequests: MutableSet<WeekDayUploadRequest>? = null,
)

data class WeekDayUploadRequest(
    val dayNumber: Int,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val startTime: Date? = null,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val endTime: Date? = null,
)

data class ScheduleTaskStatusUpdateRequest(
    val status: TaskStatus,
    val schedules: MutableSet<Schedule>
)

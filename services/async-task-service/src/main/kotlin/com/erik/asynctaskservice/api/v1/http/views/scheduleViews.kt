package com.erik.asynctaskservice.api.v1.http.views

import com.erik.common.market.Schedule
import com.erik.common.market.WeekDay
import com.erik.common.scheduleuploadtask.ScheduleUploadTask
import com.erik.common.scheduleuploadtask.TaskStatus
import com.erik.common.user.User
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

data class ScheduleView(
    val id: Long,
    val marketId: Long,
    var weekDays: MutableSet<WeekDayView>,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val lastChange: Date? = null,
    val lastChangeAuthor: UserView? = null
)

data class WeekDayView(
    val id: Long,
    val dayNumber: Int,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val startTime: Date? = null,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val endTime: Date? = null,
    val scheduleId: Long,
)

data class UserView(
    val id: Long,
    val username: String,
)

fun Schedule?.toView(): ScheduleView? {
    if (this == null)
        return null
    return ScheduleView(
        id,
        market.id,
        weekDays.map {
            it.toView()
        }.toMutableSet(),
        lastChange,
        lastChangeAuthor.toView(),
    )
}

fun WeekDay.toView(): WeekDayView {
    return WeekDayView(
        id, dayNumber, startTime, endTime, schedule.id
    )
}

fun User?.toView(): UserView? {
    if (this == null)
        return null
    return UserView(id, username)
}

data class ScheduleUploadTaskView(
    val id: Long,
    var status: TaskStatus,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val startedAt: Date,
    var schedules: MutableSet<Long>? = null,
)

fun ScheduleUploadTask.toView(): ScheduleUploadTaskView {
    return ScheduleUploadTaskView(
        id, status, startedAt, schedules?.map {
            it.id
        }?.toMutableSet()
    )
}

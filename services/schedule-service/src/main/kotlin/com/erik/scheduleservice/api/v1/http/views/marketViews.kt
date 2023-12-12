package com.erik.scheduleservice.api.v1.http.views

import com.erik.common.market.Market
import com.erik.common.market.Schedule
import com.erik.common.market.WeekDay
import com.erik.common.user.User
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class MarketView(
    val id: Long,
    val name: String,
    val description: String,
    val isActive: Boolean,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val lastChange: Date? = null,
    val lastChangeAuthor: UserView? = null,
    val schedule: ScheduleView? = null,
)

data class ScheduleView(
    val id: Long,
    val marketId: Long,
    val weekDays: MutableSet<WeekDayView>,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val lastChange: Date? = null,
    val lastChangeAuthor: UserView? = null
)

data class WeekDayView(
    val id: Long,
    val day: String,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val startTime: Date,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val endTime: Date,
    val scheduleId: Long,
)

data class UserView(
    val id: Long,
    val username: String,
)

fun Market.toView(): MarketView {
    return MarketView(
        id, name, description, isActive, lastChange, lastChangeAuthor.toView(), schedule.toView()
    )
}

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
        id, day, startTime, endTime, schedule.id
    )
}

fun User?.toView(): UserView? {
    if (this == null)
        return null
    return UserView(id, username)
}

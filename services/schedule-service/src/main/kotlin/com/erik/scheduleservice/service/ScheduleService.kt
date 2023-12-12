package com.erik.scheduleservice.service

import com.erik.common.exception.NotFoundException
import com.erik.common.market.Schedule
import com.erik.common.market.WeekDay
import com.erik.common.user.UserService
import com.erik.scheduleservice.api.v1.http.requests.ScheduleUploadRequest
import com.erik.scheduleservice.dao.MarketRepository
import com.erik.scheduleservice.dao.ScheduleRepository
import com.erik.scheduleservice.dao.WeekDayRepository
import com.erik.scheduleservice.security.getUserFromContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class ScheduleService(
    private val marketRepository: MarketRepository,
    private val scheduleRepository: ScheduleRepository,
    private val userService: UserService,
) {
    fun getScheduleById(id: Long): Schedule {
        return scheduleRepository.findById(id).orElseThrow { NotFoundException("Schedule not found") }
    }

    fun createSchedule(request: ScheduleUploadRequest): Schedule {
        val market = marketRepository.findById(request.marketId).orElseThrow { NotFoundException() }
        val schedule = Schedule(
            id = 0L,
            market = market
        )
        schedule.lastChange = Date()

        val contextUser = getUserFromContext()
        val user = userService.findByUsername(contextUser.username)
        schedule.lastChangeAuthor = user

        schedule.weekDays = mutableSetOf()

        return save(schedule)
    }

    @Transactional
    fun updateSchedule(id: Long, request: ScheduleUploadRequest): Schedule {
        val market = marketRepository.findById(request.marketId).orElseThrow { NotFoundException() }
        val schedule = Schedule(
            id = id,
            market = market
        )
        schedule.lastChange = Date()

        val contextUser = getUserFromContext()
        val user = userService.findByUsername(contextUser.username)
        schedule.lastChangeAuthor = user

        if (request.weekDayUploadRequests != null) {
            val weekDays = request.weekDayUploadRequests.map {
                WeekDay(
                    id = 0L,
                    day = it.day,
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

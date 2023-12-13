package com.erik.scheduleservice.service

import com.erik.common.exception.AlreadyExistsException
import com.erik.common.exception.NotFoundException
import com.erik.common.market.MarketRepository
import com.erik.common.market.Schedule
import com.erik.common.market.ScheduleRepository
import com.erik.common.market.WeekDay
import com.erik.common.security.getUserFromContext
import com.erik.common.user.UserService
import com.erik.scheduleservice.api.v1.http.requests.ScheduleUploadRequest
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
        if (market.schedule != null) {
            throw AlreadyExistsException("Schedule already exists")
        }

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

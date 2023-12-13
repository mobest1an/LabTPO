package com.erik.common.market

import com.erik.common.market.Market
import com.erik.common.market.Schedule
import com.erik.common.market.WeekDay
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MarketRepository : CrudRepository<Market, Long> {
    fun findAllByIsActiveTrue(): List<Market>
}

@Repository
interface ScheduleRepository : CrudRepository<Schedule, Long>

@Repository
interface WeekDayRepository : CrudRepository<WeekDay, Long>

package com.erik.scheduleservice.api.v1.http.requests

import com.erik.common.market.Market
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class MarketUploadRequest(
    val name: String,
    val description: String,
    val isActive: Boolean,
) {
    fun toMarket(id: Long = 0L): Market {
        return Market(
            id = id,
            name = name,
            description = description,
            isActive = isActive,
        )
    }
}

data class ScheduleUploadRequest(
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

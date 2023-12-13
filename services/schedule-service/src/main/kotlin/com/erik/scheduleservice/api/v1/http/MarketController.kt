package com.erik.scheduleservice.api.v1.http

import com.erik.scheduleservice.api.v1.http.views.MarketView
import com.erik.scheduleservice.api.v1.http.views.toView
import com.erik.scheduleservice.service.MarketService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/market")
class MarketController(
    private val marketService: MarketService,
) {

    @GetMapping("/{id}")
    fun getMarketById(@PathVariable id: Long): MarketView {
        val market = marketService.getMarketById(id).toView()
        if (market.schedule?.weekDays != null) {
            market.schedule.weekDays = market.schedule.weekDays.sortedBy { it.dayNumber }.toMutableSet()
        }

        return market
    }

    @GetMapping
    fun getAllVisible(): List<MarketView> {
        return marketService.getAllVisible().map {
            it.toView()
        }
    }
}

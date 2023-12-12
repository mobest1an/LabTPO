package com.erik.scheduleservice.api.v1.http

import com.erik.scheduleservice.api.v1.http.views.MarketView
import com.erik.scheduleservice.api.v1.http.views.toView
import com.erik.scheduleservice.service.MarketService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/market")
class MarketController(
    private val marketService: MarketService,
) {

    @GetMapping
    fun getAllVisible(): List<MarketView> {
        return marketService.getAllVisible().map {
            it.toView()
        }
    }
}

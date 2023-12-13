package com.erik.scheduleservice.api.v1.http

import com.erik.scheduleservice.api.v1.http.requests.MarketUploadRequest
import com.erik.scheduleservice.api.v1.http.views.MarketView
import com.erik.scheduleservice.api.v1.http.views.toView
import com.erik.scheduleservice.service.MarketService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/market/admin")
class MarketAdminController(
    private val marketService: MarketService,
) {

    @GetMapping
    fun getAll(): List<MarketView> {
        return marketService.getAll().map {
            it.toView()
        }
    }

    @PostMapping
    fun createMarket(@RequestBody request: MarketUploadRequest): MarketView {
        return marketService.createMarket(request).toView()
    }

    @PutMapping("/{id}")
    fun updateMarket(@PathVariable id: Long, @RequestBody request: MarketUploadRequest): MarketView {
        return marketService.updateMarket(id, request).toView()
    }

    @DeleteMapping("/{id}")
    fun deleteMarket(@PathVariable id: Long) {
        marketService.deleteMarket(id)
    }
}

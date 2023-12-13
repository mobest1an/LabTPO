package com.erik.scheduleservice.service

import com.erik.common.exception.NotFoundException
import com.erik.common.market.Market
import com.erik.common.market.MarketRepository
import com.erik.common.security.getUserFromContext
import com.erik.common.user.UserService
import com.erik.scheduleservice.api.v1.http.requests.MarketUploadRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MarketService(
    private val marketRepository: MarketRepository,
    private val userService: UserService,
) {

    fun getMarketById(id: Long): Market {
        return marketRepository.findById(id).orElseThrow { NotFoundException("Market not found") }
    }

    fun createMarket(request: MarketUploadRequest): Market {
        val market = request.toMarket()
        market.lastChange = Date()

        val contextUser = getUserFromContext()
        val user = userService.findByUsername(contextUser.username)
        market.lastChangeAuthor = user

        return save(market)
    }

    @Transactional
    fun updateMarket(id: Long, request: MarketUploadRequest): Market {
        val market = request.toMarket(id)
        market.lastChange = Date()

        val contextUser = getUserFromContext()
        val user = userService.findByUsername(contextUser.username)
        market.lastChangeAuthor = user

        return save(market)
    }

    fun deleteMarket(id: Long) {
        marketRepository.deleteById(id)
    }

    fun getAll(): List<Market> {
        return marketRepository.findAll().toList()
    }

    fun getAllVisible(): List<Market> {
        return marketRepository.findAllByIsActiveTrue()
    }

    private fun save(market: Market): Market {
        return marketRepository.save(market)
    }
}

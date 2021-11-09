package com.epam.contest.documentservice.service

import com.epam.contest.documentservice.dto.Stock
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface StockService {
    fun getAll(): Flux<Stock>
    fun save(stock: Stock): Mono<Boolean>
}

@Service
internal class StockServiceImpl(
    private val mongoOperations: ReactiveMongoOperations
): StockService {

    override fun getAll(): Flux<Stock> =
        mongoOperations.findAll(Stock::class.java)

    @Transactional
    override fun save(stock: Stock): Mono<Boolean> =
        mongoOperations.save(stock)
            .map { it.status }
}

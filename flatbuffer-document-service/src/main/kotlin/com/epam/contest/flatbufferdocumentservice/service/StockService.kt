package com.epam.contest.flatbufferdocumentservice.service

import epam.contest.stock.Stock
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

interface StockService {
    fun save(binaryDoc: Stock): Mono<Boolean>
}

@Service
internal class StockServiceImpl(
    private val mongoOperations: ReactiveMongoOperations
) : StockService {

    @Transactional
    override fun save(binaryDoc: Stock): Mono<Boolean> =
        binaryDoc.convert()
            .let { mongoOperations.save(it) }
            .map { it.status }
}

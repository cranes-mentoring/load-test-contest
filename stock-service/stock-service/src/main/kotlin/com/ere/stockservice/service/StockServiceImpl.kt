package com.ere.stockservice.service

import com.ere.stockservice.adapter.toDto
import com.ere.stockservice.adapter.toEntity
import com.ere.stockservice.domain.Stock
import com.ere.stockservice.domain.StockDto
import com.ere.stockservice.port.StockService
import mu.KLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StockServiceImpl(private val mongoOperations: ReactiveMongoOperations) : StockService {

    private companion object : KLogging()

    override fun getAll(page: Int, size: Int): Flux<StockDto> {
        val pageableRequest = PageRequest.of(page, size)
        val query = Query().with(pageableRequest)
        return mongoOperations.find(query, Stock::class.java)
            .map { it.toDto() }
    }

    override fun save(stock: StockDto): Mono<StockDto> =
        mongoOperations.save(stock.toEntity())
            .map { it.toDto() }
            .also { logger().debug { "Stock was saved to db: $stock" } }
}

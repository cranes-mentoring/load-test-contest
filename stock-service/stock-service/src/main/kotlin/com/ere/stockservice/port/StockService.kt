package com.ere.stockservice.port

import com.ere.stockservice.domain.StockDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface StockService {

    fun getAll(page: Int, size: Int): Flux<StockDto>
    fun save(stock: StockDto): Mono<StockDto>
}

package com.ere.stockservice.config.init

import com.ere.stockservice.adapter.db.StockRepository
import com.ere.stockservice.domain.Stock
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import java.math.BigDecimal
import java.util.*

@Configuration
class InitConfig(
    private val stockRepository: StockRepository
) {

    private companion object {
        const val INIT_SIZE = 150_000
    }

    @EventListener(ApplicationReadyEvent::class)
    fun init() = runBlocking {
        val stocks = stockRepository.findAll().toList()
        if (stocks.isNotEmpty()) {
            // ok
        } else {
            generateSequence {
                Stock(
                    id = null,
                    name = UUID.randomUUID().toString(),
                    price = BigDecimal.TWO,
                    description = UUID.randomUUID().toString()
                )
            }.take(INIT_SIZE).let {
                stockRepository.saveAll(it.asFlow())
            }
        }
    }
}
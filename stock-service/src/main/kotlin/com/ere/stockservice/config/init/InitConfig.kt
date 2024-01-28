package com.ere.stockservice.config.init

import com.ere.stockservice.adapter.db.StockRepository
import com.ere.stockservice.domain.Stock
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.core.io.ResourceLoader
import java.io.File

@Configuration
class InitConfig(
    private val stockRepository: StockRepository,
    private val resourceLoader: ResourceLoader
) {

    private companion object {
        const val READER_LIMIT = 4
    }

    private val logger = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    fun firstStartInit() = runBlocking {
        if (stockRepository.findAll().toList().isEmpty()) {
            loadMockData()
        }
    }

    private suspend fun loadMockData() {
        val resource = resourceLoader.getResource("classpath:mock-data/nasdaq.csv")
        val reader = resource.inputStream.bufferedReader()
        val headers = withContext(Dispatchers.IO) { reader.readLine() }
        logger.debug { "reading the docs and headers: $headers" }

        reader.lineSequence()
                .filter { it.isNotBlank() }
                .map { r ->
                    val (symbol, name, lastSale) = r.split(',', ignoreCase = false, limit = READER_LIMIT)
                    Stock(
                            id = null,
                            symbol = symbol,
                            price = lastSale.substring(1).toBigDecimal(),
                            description = name
                    )
                }.asFlow()
                .let { stocks -> stockRepository.saveAll(stocks) }
                .collect()

        logger.debug { "saved!" }
    }
}
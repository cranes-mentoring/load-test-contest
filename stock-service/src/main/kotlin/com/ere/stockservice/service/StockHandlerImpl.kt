package com.ere.stockservice.service

import com.ere.stockservice.domain.StockDto
import com.ere.stockservice.port.StockHandler
import com.ere.stockservice.port.StockService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.buildAndAwait

@Service
class StockHandlerImpl(
    private val stockService: StockService
) : StockHandler {

    private val logger = KotlinLogging.logger {}

    private companion object {
        const val DEFAULT_SIZE = 10
        const val NAME_PARAM = "symbol"
    }

    override suspend fun findAll(req: ServerRequest): ServerResponse {
        logger.debug { "Processing find all request: $req" }

        val stocks = stockService.getAll(DEFAULT_SIZE)
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(stocks, StockDto::class.java)
            .awaitSingle()
    }

    override suspend fun find(req: ServerRequest): ServerResponse {
        logger.debug { "Processing find all request: $req" }

        val name = req.queryParam(NAME_PARAM)

        return if (name.isEmpty) {
            ServerResponse.badRequest().buildAndAwait()
        } else {
            val stocks = stockService.find(name.get())

            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stocks, StockDto::class.java)
                .awaitSingle()

        }
    }

    override suspend fun save(req: ServerRequest): ServerResponse {
        logger.debug { "Processing save request: $req" }

        val stockDto = req.awaitBodyOrNull(StockDto::class)
        return stockDto?.let { dto ->
            stockService.save(dto)

            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .awaitSingle()
        } ?: ServerResponse.badRequest().buildAndAwait()
    }

}

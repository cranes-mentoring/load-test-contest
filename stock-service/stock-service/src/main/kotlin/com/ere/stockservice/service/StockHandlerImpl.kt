package com.ere.stockservice.service

import com.ere.stockservice.domain.StockDto
import com.ere.stockservice.port.StockHandler
import com.ere.stockservice.port.StockService
import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.buildAndAwait

@Service
class StockHandlerImpl(private val stockService: StockService) : StockHandler {

    private companion object : KLogging() {
        const val PAGE_PATH_PARAM = "page"
        const val SIZE_PATH_PARAM = "size"
        const val DEFAULT_SIZE = 10
    }

    override suspend fun save(req: ServerRequest): ServerResponse {
        val stockDto = req.awaitBodyOrNull(StockDto::class)

        logger().debug { "Processing save request: $req" }

        return stockDto?.let { dto ->
            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockService.save(dto))
                .awaitSingle()
        } ?: ServerResponse.badRequest().buildAndAwait()
    }

    override suspend fun findAll(req: ServerRequest): ServerResponse {
        val page = Integer.parseInt(req.pathVariable(PAGE_PATH_PARAM))
        val size = Integer.parseInt(req.pathVariable(SIZE_PATH_PARAM)).takeIf { it > 0 } ?: DEFAULT_SIZE

        logger().debug { "Processing search request: $req" }

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(stockService.getAll(page, size))
            .awaitSingle()
    }
}

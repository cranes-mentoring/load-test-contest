package com.ere.stockservice.port

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

interface StockHandler {

    suspend fun save(req: ServerRequest): ServerResponse
    suspend fun findAll(req: ServerRequest): ServerResponse
}

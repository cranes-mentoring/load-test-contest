package com.ere.stockservice.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class StockControllerAdvice {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    suspend fun handleException(ex: Exception) {
        logger.error { "Problem with: $ex" }
    }
}

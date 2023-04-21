package com.ere.stockservice.config

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class StockControllerAdvice {

    private companion object : KLogging()

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    suspend fun handleException(ex: Exception) {
        logger.error { "Problem with: $ex" }
    }
}

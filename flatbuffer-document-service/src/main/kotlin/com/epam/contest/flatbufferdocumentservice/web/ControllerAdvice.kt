package com.epam.contest.flatbufferdocumentservice.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    fun handleException(ex: Exception) {
        logger.error("problem with: $ex")
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}

package com.epam.contest.flatbufferdocumentservice.web

import com.epam.contest.flatbufferdocumentservice.service.StockService
import com.epam.contest.flatbufferdocumentservice.web.StockController.Companion.MAIN_URL
import epam.contest.stock.Stock
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(MAIN_URL)
@CrossOrigin
class StockController(
    private val stockService: StockService
) {

    @PostMapping
    fun saveDocument(@RequestBody binaryDoc: Stock): Mono<Any> =
        stockService.save(binaryDoc)
            .map { status -> ResponseEntity.ok(status) }

    companion object {
        const val MAIN_URL = "document/v2/stock/"
    }
}

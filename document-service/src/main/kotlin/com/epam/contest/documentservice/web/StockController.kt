package com.epam.contest.documentservice.web

import com.epam.contest.documentservice.dto.Stock
import com.epam.contest.documentservice.service.StockService
import com.epam.contest.documentservice.web.StockController.Companion.MAIN_URL
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(MAIN_URL)
@CrossOrigin
class StockController(
    private val stockService: StockService
) {

    @GetMapping
    fun getAll(): Flux<Stock> = stockService.getAll()

    @PostMapping
    fun saveDocument(@RequestBody document: Stock): Mono<Any> =
        stockService.save(document)
            .map { status -> ResponseEntity.ok(status) }

    companion object {
        const val MAIN_URL = "document/v1/stock/"
    }
}

package com.epam.contest.documentservice.web

import com.epam.contest.documentservice.defaultStock
import com.epam.contest.documentservice.newStock
import com.epam.contest.documentservice.service.StockService
import com.epam.contest.documentservice.web.StockController.Companion.MAIN_URL
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(StockController::class)
internal class StockControllerTest {

    @MockBean
    lateinit var stockService: StockService

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return all available stocks`() {
        Mockito.`when`(stockService.getAll()).thenReturn(Flux.just(defaultStock))

        webClient
            .get()
            .uri("/$MAIN_URL")
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .exchange()
            .expectStatus().is2xxSuccessful
    }

    @Test
    fun `should create new one`() {
        val body = BodyInserters.fromValue(objectMapper.writeValueAsString(newStock))
        Mockito.`when`(stockService.save(newStock)).thenReturn(Mono.just(true))

        webClient
            .post()
            .uri("/$MAIN_URL")
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .body(body)
            .exchange()
            .expectStatus().is2xxSuccessful
    }
}
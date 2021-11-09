package com.epam.contest.flatbufferdocumentservice.web

import com.epam.contest.flatbufferdocumentservice.binaryDoc
import com.epam.contest.flatbufferdocumentservice.service.StockService
import com.epam.contest.flatbufferdocumentservice.web.StockController.Companion.MAIN_URL
import com.fasterxml.jackson.databind.ObjectMapper
import epam.contest.stock.Stock
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.buffer.*
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@Disabled("TODO")
@WebFluxTest(StockController::class)
internal class StockControllerTest {

    @MockBean
    lateinit var stockService: StockService

    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun `should save new stock`() {
        Mockito.`when`(stockService.save(binaryDoc)).thenReturn(Mono.just(true))

        webClient
            .post()
            .uri("/$MAIN_URL")
            .header(HttpHeaders.CONTENT_TYPE, "application/x-flatbuffers")
            .body(String(binaryDoc.byteBuffer.array()), String::class.java)
            .exchange()
            .expectStatus().is2xxSuccessful
    }
}

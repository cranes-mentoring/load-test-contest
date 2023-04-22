package com.ere.stockservice.web

import com.ere.stockservice.adapter.toDto
import com.ere.stockservice.config.IntegrationTest
import com.ere.stockservice.config.ServerProperties
import com.ere.stockservice.config.StockRouter
import com.ere.stockservice.domain.Stock
import com.ere.stockservice.domain.StockDto
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import org.springframework.web.reactive.function.BodyInserters.fromValue
import java.math.BigDecimal
import java.util.UUID
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class StockControllerTest(
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val stockRouter: StockRouter,
    @Autowired private val properties: ServerProperties,
    @Autowired private val mongoOperations: ReactiveMongoOperations
) : IntegrationTest() {

    private val testScope = TestScope()
    private lateinit var client: WebTestClient

    @BeforeEach
    fun before() {
        client = WebTestClient.bindToRouterFunction(stockRouter.router()).build()
        mongoOperations.dropCollection<Stock>().subscribe()
    }

    @Test
    fun `should return all`() = testScope.runTest {
        val path = properties.main + properties.findAll

        val size = 10
        stocksSequence().take(size).toList().let { list ->
            mongoOperations.insertAll(list).subscribe()
        }

        client
            .get().uri(path, "0", size)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk
            .expectBodyList<StockDto>().hasSize(size)
    }

    @Test
    fun `should save new stock`() = testScope.runTest {
        val stock = stocksSequence().first().toDto()
        val json = objectMapper.writeValueAsString(stock)

        client
            .post()
            .uri(properties.main)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(json)
            .exchange()
            .expectStatus()
            .isOk

        mongoOperations.findAll(Stock::class.java).asClue {
            it.collectList().awaitSingle().size shouldNotBe 0
        }
    }

    @Test
    fun `should not save new stock`() = testScope.runTest {
        client
            .post()
            .uri(properties.main)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromValue { "" })
            .exchange()
            .expectStatus()
            .isBadRequest
    }

    private suspend fun stocksSequence(): Sequence<Stock> = generateSequence {
        Stock(
            id = null,
            name = UUID.randomUUID().toString(),
            price = BigDecimal.valueOf(Random.nextLong()),
            amount = Random.nextInt().toShort(),
            description = UUID.randomUUID().toString(),
            status = true
        )
    }
}

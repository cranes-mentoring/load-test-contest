package com.epam.contest.documentservice.service

import com.epam.contest.documentservice.defaultStock
import com.epam.contest.documentservice.dto.Stock
import com.epam.contest.documentservice.newStock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class StockServiceImplTest {

   private val reactiveMongoOperations = mockk<ReactiveMongoOperations>()

    private val stockService by lazy {
        StockServiceImpl(reactiveMongoOperations)
    }

    @Test
    fun `should return all`() {
        every {
            reactiveMongoOperations.findAll(Stock::class.java)
        } returns Flux.just(defaultStock)

        StepVerifier
            .create(stockService.getAll())
            .expectSubscription()
            .expectNext(defaultStock)
            .verifyComplete()

        verify { reactiveMongoOperations.findAll(Stock::class.java) }
    }

    @Test
    fun `should save new`() {
        every { reactiveMongoOperations.save(newStock) } returns Mono.just(newStock.copy(id = "d"))

        StepVerifier
            .create(stockService.save(newStock))
            .expectSubscription()
            .expectNext(true)
            .verifyComplete()

        verify { reactiveMongoOperations.save(newStock) }
    }
}
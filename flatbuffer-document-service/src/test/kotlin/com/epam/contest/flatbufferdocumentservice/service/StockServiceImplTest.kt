package com.epam.contest.flatbufferdocumentservice.service

import com.epam.contest.flatbufferdocumentservice.binaryDoc
import com.epam.contest.flatbufferdocumentservice.defaultStock
import com.epam.contest.flatbufferdocumentservice.dto.Stock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class StockServiceImplTest {

    private val reactiveMongoOperations = mockk<ReactiveMongoOperations>()

    private val stockService by lazy {
        StockServiceImpl(reactiveMongoOperations)
    }

    @Test
    fun `should save new stock`() {
        every {
            reactiveMongoOperations.save(any<Stock>())
        } returns Mono.just(defaultStock)

        StepVerifier
            .create(stockService.save(binaryDoc))
            .expectSubscription()
            .expectNext(true)
            .verifyComplete()

        verify { reactiveMongoOperations.save(any<Stock>()) }
    }
}

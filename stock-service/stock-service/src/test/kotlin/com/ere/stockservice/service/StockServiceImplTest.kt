package com.ere.stockservice.service

import com.ere.stockservice.adapter.toDto
import com.ere.stockservice.domain.Stock
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
internal class StockServiceImplTest {

    private val mongoOperator = mockk<ReactiveMongoOperations>()
    private val service = StockServiceImpl(mongoOperator)

    private val testScope = TestScope()

    @Test
    fun `should find all`() = testScope.runTest {
        coEvery {
            mongoOperator.find(any(), Stock::class.java)
        } returns Flux.just(entity)

        service.getAll(0, 10).asClue {
            it.awaitSingle() shouldNotBe null
        }
    }

    @Test
    fun `should save`() = testScope.runTest {
        coEvery {
            mongoOperator.save(any<Stock>())
        } returns Mono.just(entity)

        service.save(entity.toDto()).asClue {
            it.awaitSingle() shouldNotBe null
        }
    }

    private companion object {
        val entity = Stock("id", "test", BigDecimal.ONE, 1, "f", true)
    }
}

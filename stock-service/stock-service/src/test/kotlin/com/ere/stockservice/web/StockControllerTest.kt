package com.ere.stockservice.web

import com.ere.stockservice.config.IntegrationTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StockControllerTest: IntegrationTest() {

    private val testScope = TestScope()

    @Test
    fun `should return all`() = testScope.runTest {

    }

    @Test
    fun `should save new`() = testScope.runTest {

    }
}

package com.ere.stockservice.adapter.db

import com.ere.stockservice.domain.Stock
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface StockRepository: CoroutineCrudRepository<Stock, Long> {

    @Query("""select * from stocks limit :limit""")
    fun findAllByLimit(limit: Int): Flow<Stock>

    fun findAllBySymbol(symbol: String): Flow<Stock>
}

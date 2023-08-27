package com.ere.stockservice.port

import com.ere.stockservice.domain.Stock
import com.ere.stockservice.domain.StockDto
import kotlinx.coroutines.flow.Flow

interface StockService {
    fun getAll(size: Int): Flow<StockDto>
    suspend fun save(stock: StockDto): StockDto
    suspend fun find(name: String): Flow<StockDto>
}

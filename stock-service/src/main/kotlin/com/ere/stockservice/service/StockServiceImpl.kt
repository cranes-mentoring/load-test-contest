package com.ere.stockservice.service

import com.ere.stockservice.adapter.db.StockRepository
import com.ere.stockservice.adapter.toDto
import com.ere.stockservice.adapter.toEntity
import com.ere.stockservice.domain.StockDto
import com.ere.stockservice.port.StockService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class StockServiceImpl(
    private val stockRepository: StockRepository
) : StockService {

    override fun getAll(size: Int): Flow<StockDto> =
        stockRepository.findAllByLimit(size).map { it.toDto() }

    override suspend fun save(stock: StockDto): StockDto =
        stockRepository.save(stock.toEntity()).toDto()

    override suspend fun find(symbol: String): Flow<StockDto> =
        stockRepository.findAllBySymbol(symbol).map { it.toDto() }
}
package com.ere.stockservice.domain

import java.math.BigDecimal

data class StockDto(
    val name: String,
    val price: BigDecimal,
    val description: String
)

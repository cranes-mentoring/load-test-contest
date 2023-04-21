package com.ere.stockservice.domain

import java.math.BigDecimal

data class StockDto(
    val name: String,
    val price: BigDecimal,
    val amount: Short,
    val description: String,
    val status: Boolean
)

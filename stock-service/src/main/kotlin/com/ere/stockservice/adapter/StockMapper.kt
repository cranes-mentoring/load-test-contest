package com.ere.stockservice.adapter

import com.ere.stockservice.domain.Stock
import com.ere.stockservice.domain.StockDto

fun Stock.toDto() = StockDto(
    name = this.name,
    price = this.price,
    description = this.description
)

fun StockDto.toEntity() = Stock(
    id = null,
    name = this.name,
    price = this.price,
    description = this.description
)

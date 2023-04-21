package com.ere.stockservice.adapter

import com.ere.stockservice.domain.Stock
import com.ere.stockservice.domain.StockDto

fun Stock.toDto() = StockDto(
    name = this.name,
    price = this.price,
    amount = this.amount,
    description = this.description,
    status = this.status
)

fun StockDto.toEntity() = Stock(
    id = null,
    name = this.name,
    price = this.price,
    amount = this.amount,
    description = this.description,
    status = this.status
)

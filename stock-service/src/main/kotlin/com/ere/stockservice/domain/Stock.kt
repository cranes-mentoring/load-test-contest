package com.ere.stockservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("stocks")
data class Stock(
    @field:Id val id: Long?,
    val symbol: String,
    val price: BigDecimal,
    val description: String
)

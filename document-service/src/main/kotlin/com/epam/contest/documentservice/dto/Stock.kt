package com.epam.contest.documentservice.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document("stock")
data class Stock(
    @field:Id val id: String?,
    val name: String,
    val price: BigDecimal,
    val amount: Short,
    val description: String,
    val status: Boolean
)

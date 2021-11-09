package com.epam.contest.flatbufferdocumentservice.service

import epam.contest.stock.Stock
import java.math.BigDecimal
import com.epam.contest.flatbufferdocumentservice.dto.Stock as dtoStock

fun Stock.convert() : dtoStock =
    dtoStock(id = null, name = this.name(),
        price = BigDecimal.valueOf(this.price().toDouble()),
        amount = this.amount(),
        description = this.description(),
        status = this.status()
    )

package com.epam.contest.documentservice

import com.epam.contest.documentservice.dto.Stock
import java.math.BigDecimal

val newStock = Stock(null, "TEMP", BigDecimal.ZERO, 1,"descr", true)
val defaultStock = newStock.copy(id = "temp")
package com.ere.stockservice.domain

import java.math.BigDecimal

val newStock = Stock(null, "TEMP", BigDecimal.ZERO, 1, "descr", true)
val defaultStock = newStock.copy(id = "temp")

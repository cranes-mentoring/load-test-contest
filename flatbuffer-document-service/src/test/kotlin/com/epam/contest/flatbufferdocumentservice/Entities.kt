package com.epam.contest.flatbufferdocumentservice

import com.epam.contest.flatbufferdocumentservice.dto.Stock
import java.io.File
import java.math.BigDecimal
import java.nio.ByteBuffer

val newStock = Stock(null, "TEMP", BigDecimal.ZERO, 1, "descr", true)
val defaultStock = newStock.copy(id = "temp")

val binaryDoc: epam.contest.stock.Stock by lazy {
    val file = File("new.uu")
    file.readBytes()
        .let { ba ->
            epam.contest.stock.Stock.getRootAsStock(ByteBuffer.wrap(ba))
        }
}

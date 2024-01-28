package com.ere.stockservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockService

fun main(args: Array<String>) {
    runApplication<StockService>(*args)
}
 
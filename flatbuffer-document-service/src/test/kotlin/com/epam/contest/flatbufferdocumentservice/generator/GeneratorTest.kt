package com.epam.contest.flatbufferdocumentservice.generator

import com.google.flatbuffers.FlatBufferBuilder
import epam.contest.stock.Stock
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

@Disabled("simulate")
class GeneratorTest {

    private fun create(): FlatBufferBuilder {
        logger.info("Start generation...")
        val fbb = FlatBufferBuilder(1024)
        val name = fbb.createString("FI-SCB-12314")
        val price = fbb.createString("1.14")
        val amount: Short = 2
        val descr = fbb.createString("description")
        val status = true

        Stock.startStock(fbb)
        Stock.addName(fbb, name)
        Stock.addPrice(fbb, price)
        Stock.addAmount(fbb, amount)
        Stock.addDescription(fbb, descr)
        Stock.addStatus(fbb, status)
        val stock = Stock.endStock(fbb)
        fbb.finish(stock)
        return fbb
    }

    @Test
    fun `should create new request as flatbuffer`() {
        val fbb = create()
        val file = File("binary.uu")
        file.writeBytes(fbb.sizedByteArray())
        logger.info("end test...")
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}

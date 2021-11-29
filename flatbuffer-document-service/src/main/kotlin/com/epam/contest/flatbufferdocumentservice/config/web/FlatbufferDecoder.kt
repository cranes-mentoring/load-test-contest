package com.epam.contest.flatbufferdocumentservice.config.web

import com.google.flatbuffers.Table
import epam.contest.stock.Stock
import org.reactivestreams.Publisher
import org.springframework.core.ResolvableType
import org.springframework.core.codec.Decoder
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.util.MimeType
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class FlatbufferDecoder : FlatbuffersCodecSupport(), Decoder<Table> {

    private val maxMessageSize = DEFAULT_MESSAGE_MAX_SIZE

    override fun canDecode(elementType: ResolvableType, mimeType: MimeType?): Boolean =
        Table::class.java.isAssignableFrom(elementType.toClass()) && supportsMimeType(mimeType)

    override fun decode(
        inputStream: Publisher<DataBuffer>,
        elementType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Flux<Table> =
        Flux.from(inputStream)
            .map { decode(it, elementType, mimeType, hints) }

    override fun decodeToMono(
        inputStream: Publisher<DataBuffer>,
        elementType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Mono<Table> =
        DataBufferUtils.join(inputStream, maxMessageSize)
            .map { decode(it, elementType, mimeType, hints) }

    override fun decode(
        buffer: DataBuffer,
        targetType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Table = Stock.getRootAsStock(buffer.asByteBuffer())

    override fun getDecodableMimeTypes(): MutableList<MimeType> = MIME_TYPES
}

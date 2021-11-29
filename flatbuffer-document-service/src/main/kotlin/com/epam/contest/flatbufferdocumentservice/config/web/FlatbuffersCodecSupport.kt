package com.epam.contest.flatbufferdocumentservice.config.web

import org.springframework.lang.Nullable
import org.springframework.util.MimeType

abstract class FlatbuffersCodecSupport {

    companion object {
        val MIME_TYPES: MutableList<MimeType> =
            mutableListOf(
                MimeType("application", "x-flatbuffers"),
                MimeType("application", "x-flatbuffer"),
            )
        const val DEFAULT_MESSAGE_MAX_SIZE = 256 * 1024
    }

    protected open fun supportsMimeType(@Nullable mimeType: MimeType?): Boolean =
        mimeType == null || MIME_TYPES.any { m: MimeType -> m.isCompatibleWith(mimeType) }

    protected open fun getMimeTypes(): List<MimeType> = MIME_TYPES
}

package com.mizukikubota.tsundokubreak.domain.entity.common

import com.mizukikubota.tsundokubreak.domain.service.JsonHandler

data class ErrorBody(
    val error: List<ErrorContent>
) {
    companion object {
        fun fromJson(error: String?): ErrorBody =
            JsonHandler.converter.fromJson(error, ErrorBody::class.java)
    }
}

data class ErrorContent(
    val code: Int,
    val message: String
)

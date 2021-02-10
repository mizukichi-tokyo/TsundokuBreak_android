package com.example.tsundokubreak.domain.entity.common

import com.example.tsundokubreak.domain.service.JsonHandler

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

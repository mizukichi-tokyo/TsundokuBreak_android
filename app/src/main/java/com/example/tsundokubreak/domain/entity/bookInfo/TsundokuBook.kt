package com.example.tsundokubreak.domain.entity.bookInfo

data class TsundokuBook(
    val id: Int? = 0,
    val title: String? = "",
    val author: String? = "",
    val totalPageCount: Int? = 0,
    val imageURL: String? = "",
    val readPageCount: Int? = 0,
    val dokuryo: Boolean? = false
)

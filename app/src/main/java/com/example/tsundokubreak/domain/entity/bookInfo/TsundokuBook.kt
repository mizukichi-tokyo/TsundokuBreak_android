package com.example.tsundokubreak.domain.entity.bookInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tsundokuBooks")
data class TsundokuBook(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String? = "",
    val author: String? = "",
    val totalPageCount: Int? = 0,
    val imageURL: String? = "",
    val readPageCount: Int? = 0,
    val dokuryo: Boolean? = false
)

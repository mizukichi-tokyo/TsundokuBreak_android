package com.example.tsundokubreak.domain.entity.bookInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TsundokuBook(
//    id = 0だとroomが自動で振り分けてくれます
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String? = "",
    val author: String? = "",
    val totalPageCount: Int? = 0,
    val imageURL: String? = "",
    val readPageCount: Int? = 0,
    val dokuryo: Boolean? = false
)

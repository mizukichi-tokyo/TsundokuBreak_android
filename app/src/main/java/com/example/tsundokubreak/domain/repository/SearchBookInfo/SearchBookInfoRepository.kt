package com.example.tsundokubreak.domain.repository.SearchBookInfo

import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.example.tsundokubreak.domain.entity.common.Resource
import kotlinx.coroutines.flow.Flow

interface SearchBookInfoRepository {

    suspend fun getBookInfo(): Flow<Resource<TsundokuBook>>

    suspend fun addBookToTsundoku(
        tsundokuBook: TsundokuBook,
        callback: () -> Unit,
        fallback: (Throwable) -> Unit
    )
}

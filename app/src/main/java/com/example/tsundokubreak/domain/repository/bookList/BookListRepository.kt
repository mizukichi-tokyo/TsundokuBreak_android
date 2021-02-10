package com.example.tsundokubreak.domain.repository.bookList

import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import kotlinx.coroutines.flow.Flow

interface BookListRepository {
    suspend fun getAllTsundokuBook(): Flow<List<TsundokuBook>>

    suspend fun getAllDokuryoBook(): Flow<List<TsundokuBook>>

    suspend fun updateTsundokuBook(tsundokuBook: TsundokuBook)

    suspend fun deleteDokuryoBook(tsundokuBook: TsundokuBook)
}

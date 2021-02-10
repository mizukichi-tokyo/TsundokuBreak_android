package com.example.tsundokubreak.domain.repository.bookList

import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import kotlinx.coroutines.flow.Flow

class BookListRepositoryImpl(
    private val bookListDao: BookListDao
) : BookListRepository {

    override suspend fun getAllTsundokuBook(): Flow<List<TsundokuBook>> =
        bookListDao.getTsundokuBookList()

    override suspend fun getAllDokuryoBook(): Flow<List<TsundokuBook>> =
        bookListDao.getDokuryoBookList()

    override suspend fun updateTsundokuBook(tsundokuBook: TsundokuBook) {
        bookListDao.updateTsundokuBook(tsundokuBook)
    }

    override suspend fun deleteDokuryoBook(tsundokuBook: TsundokuBook) {
        bookListDao.deleteDokuryoBook(tsundokuBook)
    }
}

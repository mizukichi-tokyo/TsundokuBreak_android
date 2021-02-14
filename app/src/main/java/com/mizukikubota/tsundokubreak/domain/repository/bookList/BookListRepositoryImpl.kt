package com.mizukikubota.tsundokubreak.domain.repository.bookList

import com.mizukikubota.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class BookListRepositoryImpl @Inject constructor(
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

package com.example.tsundokubreak.domain.repository.searchBookInfo

import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.example.tsundokubreak.domain.entity.common.Resource
import com.example.tsundokubreak.domain.repository.bookList.BookListDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchBookInfoRepositoryImpl @Inject constructor(
    private val bookListDao: BookListDao,
    private val bookInfoDataStore: SearchBookInfoDataStore
) : SearchBookInfoRepository {

    override suspend fun getBookInfo(): Flow<Resource<TsundokuBook>> =
        bookInfoDataStore.fetchBookInfo("1")

    override suspend fun addBookToTsundoku(
        tsundokuBook: TsundokuBook,
        callback: () -> Unit,
        fallback: (Throwable) -> Unit
    ) {
        runCatching {
            bookListDao.addTsundokuBook(tsundokuBook)
        }
            .onSuccess {
                callback()
            }
            .onFailure {
                fallback(it)
            }
    }
}

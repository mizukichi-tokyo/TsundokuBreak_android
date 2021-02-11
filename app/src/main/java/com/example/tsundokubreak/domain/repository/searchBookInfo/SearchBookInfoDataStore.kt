package com.example.tsundokubreak.domain.repository.searchBookInfo

import com.example.tsundokubreak.domain.entity.common.Resource
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.example.tsundokubreak.domain.entity.common.ErrorBody
import com.example.tsundokubreak.domain.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

object SearchBookInfoDataStore {

    fun fetchBookInfo(
        isbn: String?
    ): Flow<Resource<TsundokuBook>> = flow {
        emit(Resource.InProgress)
        runCatching {
            withContext(Dispatchers.IO) {
                isbn?.let {
                    ApiClient.retrofit.getBookInfo(
                        "isbn:" + isbn
                    )
                }?: run {
                    ApiClient.retrofit.getBookInfo(
                        "isbn:0"
                    )
                }
            }
        }
        .onSuccess {
            if (it.isSuccessful) {
                it.body()?.let {
                        if(it.items != null){
                            var bookInfo = TsundokuBook(
                                title = it.items.first().volumeInfo?.title,
                                author = it.items.first().volumeInfo?.authors?.first(),
                                totalPageCount = it.items.first().volumeInfo?.pageCount?.toInt(),
                                imageURL = it.items.first().volumeInfo?.imageLinks?.thumbnail,
                            )
                            emit(Resource.Success(bookInfo))
                        } else{
                            emit(Resource.Empty)
                        }
                    }
                } else {
                emit(Resource.ApiError(ErrorBody.fromJson(it.errorBody()?.string())))
            }
        }
        .onFailure {
            emit(Resource.NetworkError(it))
        }
    }.flowOn(Dispatchers.IO)
}

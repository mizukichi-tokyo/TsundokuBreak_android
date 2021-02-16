package com.mizukikubota.tsundokubreak.domain.service

import com.mizukikubota.tsundokubreak.domain.entity.bookInfo.APIBookInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiType {

        @GET("books/v1/volumes")
        suspend fun getBookInfo(
//            /?q=isbn:9784873116594
//            と続くので　isbnString は　"isbn:" + 13桁の数字
            @Query("q") isbnString: String,
        ): Response<APIBookInfoResponse>
}
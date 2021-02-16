package com.mizukikubota.tsundokubreak.domain.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val baseUrl = "https://www.googleapis.com/"

    val retrofit: IApiType by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(jsonConverter)
            .build()
            .create(IApiType::class.java)
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val jsonConverter by lazy {
        JsonHandler.converter.run { GsonConverterFactory.create(this) }
    }
}

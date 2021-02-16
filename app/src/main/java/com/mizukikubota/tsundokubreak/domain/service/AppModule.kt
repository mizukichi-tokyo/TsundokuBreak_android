package com.mizukikubota.tsundokubreak.domain.service

import android.content.Context
import androidx.room.Room
import com.mizukikubota.tsundokubreak.domain.repository.bookList.BookListDao
import com.mizukikubota.tsundokubreak.domain.repository.bookList.BookListRepository
import com.mizukikubota.tsundokubreak.domain.repository.bookList.BookListRepositoryImpl
import com.mizukikubota.tsundokubreak.domain.repository.searchBookInfo.SearchBookInfoDataStore
import com.mizukikubota.tsundokubreak.domain.repository.searchBookInfo.SearchBookInfoRepository
import com.mizukikubota.tsundokubreak.domain.repository.searchBookInfo.SearchBookInfoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideListDao(db: AppDatabase) = db.bookListDao()

    @Provides
    @Singleton
    fun provideSearchBookInfoDataStore() = SearchBookInfoDataStore

    @Provides
    @Singleton
    fun provideSearchBookInfoRepository(
        bookListDao: BookListDao,
        searchBookInfoDataStore: SearchBookInfoDataStore
    ): SearchBookInfoRepository {
        return SearchBookInfoRepositoryImpl(bookListDao, searchBookInfoDataStore)
    }

    @Provides
    @Singleton
    fun provideBookListRepository(
        bookListDao: BookListDao
    ): BookListRepository {
        return BookListRepositoryImpl(bookListDao)
    }
}

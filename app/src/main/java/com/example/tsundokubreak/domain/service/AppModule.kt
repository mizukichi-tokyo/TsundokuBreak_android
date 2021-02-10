package com.example.tsundokubreak.domain.service

import android.content.Context
import androidx.room.Room
import com.example.tsundokubreak.domain.repository.bookList.BookListDao
import com.example.tsundokubreak.domain.repository.bookList.BookListRepository
import com.example.tsundokubreak.domain.repository.bookList.BookListRepositoryImpl
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
    fun provideTsundokuBookDao(db: AppDatabase) = db.tsundokuBookDao()

    @Provides
    @Singleton
    fun provideBookListRepository(
        bookListDao: BookListDao
    ): BookListRepository {
        return BookListRepositoryImpl(bookListDao)
    }
}

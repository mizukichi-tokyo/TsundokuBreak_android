package com.mizukikubota.tsundokubreak.domain.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mizukikubota.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.mizukikubota.tsundokubreak.domain.repository.bookList.BookListDao

@Database(entities = [TsundokuBook::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookListDao(): BookListDao
}

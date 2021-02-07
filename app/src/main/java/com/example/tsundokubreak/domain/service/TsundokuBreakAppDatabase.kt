package com.example.tsundokubreak.domain.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.example.tsundokubreak.domain.repository.bookList.TsundokuBookDao

@Database(entities = [TsundokuBook::class], version = 1)
abstract class TsundokuBreakAppDatabase : RoomDatabase() {
    abstract fun tsundokuBookDao(): TsundokuBookDao
}

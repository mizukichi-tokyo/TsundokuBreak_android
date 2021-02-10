package com.example.tsundokubreak.domain.repository.bookList

import androidx.room.*
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook

@Dao
interface BookListDao {

    // SQLite have no boolean data type
    // Room maps boolean data to an INTEGER column
    // Room mapping true to 1 and false to 0
    @Query("select * from tsundokuBook where dokuryo = 0 ")
    suspend fun getTsundokuBookList(): List<TsundokuBook>

    @Query("select * from tsundokuBook where dokuryo = 1 ")
    suspend fun getDokuryoBookList(): List<TsundokuBook>

    @Insert
    suspend fun addTsundokuBook(tsundokuBook: TsundokuBook)

    @Update
    suspend fun updateTsundokuBook(tsundokuBook: TsundokuBook)

    @Delete
    suspend fun deleteTsundokuBook(tsundokuBook: TsundokuBook)
}

package com.example.tsundokubreak.domain.repository.bookList

import androidx.room.*
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook

@Dao
interface TsundokuBookDao {

//    @Query("select * from tsundokuBook where dokuryo = 'false'　ORDER BY id asc")
//    suspend fun getTsundokuBookList(): List<TsundokuBook>
//
//    @Query("select * from tsundokuBook where dokuryo = 'true' ORDER BY id asc")
//    suspend fun getDokuryoBookList(): List<TsundokuBook>

    @Insert
    suspend fun addTsundokuBook(tsundokuBook: TsundokuBook)

    @Update
    suspend fun updateTsundokuBook(tsundokuBook: TsundokuBook)

    @Delete
    suspend fun deleteTsundokuBook(tsundokuBook: TsundokuBook)
}

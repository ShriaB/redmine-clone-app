package com.example.redmine.data

import androidx.room.*
import com.example.redmine.data.model.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Insert
    suspend fun insert(entry: Entry)

    @Update
    suspend fun update(entry: Entry)

    @Delete
    suspend fun delete(entry: Entry)

    @Query("SELECT * FROM entry" )
    fun getEntries(): Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE id = :id")
    fun getEntry(id: Int): Flow<Entry>
}
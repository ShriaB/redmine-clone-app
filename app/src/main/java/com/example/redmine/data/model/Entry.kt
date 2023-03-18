package com.example.redmine.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "task")
    val task: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "hours")
    val hours: Double,
    @ColumnInfo(name = "comment")
    val comment: String
)

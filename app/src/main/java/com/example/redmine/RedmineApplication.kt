package com.example.redmine

import android.app.Application
import com.example.redmine.data.EntryRoomDatabase

class RedmineApplication: Application(){
    val database: EntryRoomDatabase by lazy{ EntryRoomDatabase.getDatabase(this)}
}
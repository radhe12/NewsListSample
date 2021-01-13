package com.radhecodes.cbctest.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.radhecodes.cbctest.repository.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "news_db"
    }

    abstract val newsDao: NewsDao
}
package com.radhecodes.cbctest.di


import androidx.room.Room
import com.radhecodes.cbctest.repository.NewRepositoryImpl
import com.radhecodes.cbctest.repository.NewsRepository
import com.radhecodes.cbctest.repository.database.AppDatabase
import com.radhecodes.cbctest.repository.database.NewsDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val myAppModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }

    factory("newsDao") { get<AppDatabase>().newsDao }
    factory("NewsRepository") {
        createNewsRepository(
            newsDao = get("newsDao")
        )
    }
}
    fun createNewsRepository(newsDao: NewsDao): NewsRepository {
        return NewRepositoryImpl(newsDao)
    }
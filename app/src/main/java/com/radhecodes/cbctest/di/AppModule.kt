package com.radhecodes.cbctest.di


import androidx.room.Room
import com.radhecodes.cbctest.repository.NewsRepository
import com.radhecodes.cbctest.repository.NewsRepositoryImpl
import com.radhecodes.cbctest.repository.database.AppDatabase
import com.radhecodes.cbctest.repository.database.NewsDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val myAppModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }

    factory { get<AppDatabase>().newsDao }
    factory {
        createNewsRepository(
            newsDao = get()
        )
    }
}
    fun createNewsRepository(newsDao: NewsDao): NewsRepository {
        return NewsRepositoryImpl(newsDao)
    }
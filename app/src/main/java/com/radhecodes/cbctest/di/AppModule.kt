package com.radhecodes.cbctest.di

import com.radhecodes.cbctest.repository.model.NewRepositoryImpl
import com.radhecodes.cbctest.repository.model.NewsRepository
import com.radhecodes.cbctest.services.NewsService
import org.koin.dsl.module.module

val myAppModule = module {

    factory("NewsRepository") {
        createNewsRepository()
    }
}
    fun createNewsRepository(): NewsRepository {
        return NewRepositoryImpl()
    }